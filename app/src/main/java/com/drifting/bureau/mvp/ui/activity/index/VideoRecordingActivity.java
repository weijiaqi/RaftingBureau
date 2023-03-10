package com.drifting.bureau.mvp.ui.activity.index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.MediaStoreOutputOptions;
import androidx.camera.video.Quality;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.di.component.DaggerVideoRecordingComponent;
import com.drifting.bureau.mvp.contract.VideoRecordingContract;
import com.drifting.bureau.mvp.presenter.VideoRecordingPresenter;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.dialog.RecordingDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.CircleProgressButtonView;
import com.google.common.util.concurrent.ListenableFuture;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.picture.photoview.PhotoView;


/**
 * @Description: ????????????
 * @Author : WeiJiaQI
 * @Time : 2022/5/16 11:49
 */
public class VideoRecordingActivity extends BaseManagerActivity<VideoRecordingPresenter> implements VideoRecordingContract.View {
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.viewFinder)
    PreviewView mViewFinder;
    @BindView(R.id.iv_start)
    ImageView mIvStart;
    @BindView(R.id.btn_record)
    CircleProgressButtonView mBtnRecord;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.rl_shoot)
    RelativeLayout mRlShoot;
    @BindView(R.id.rl_operate)
    RelativeLayout mRlOperate;
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    private VideoCapture<Recorder> videoCapture = null;
    private ImageCapture imageCapture = null;
    private ExecutorService cameraExecutor;
    private Recording recording = null;
    private static String TAG = VideoRecordingActivity.class.getSimpleName();
    private int REQUEST_CODE = 1;
    private Uri uri;
    private String path;
    private int index;
    private boolean isBack = true;


    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, VideoRecordingActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVideoRecordingComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_recording; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        TextUtil.setRightImage(mIvRight, R.drawable.photo_reversal);
        cameraExecutor = Executors.newSingleThreadExecutor();
        startCamera(true);
        mBtnRecord.setOnClickListener(new CircleProgressButtonView.OnClickListener() {
            @Override
            public void onClick() {
                takePhoto(); // ??????
            }
        });

        mBtnRecord.setOnLongClickListener(new CircleProgressButtonView.OnLongClickListener() {
            @Override
            public void onLongClick() {
                captureVideo();
            }

            @Override
            public void onNoMinRecord(int currentTime) {

            }

            @Override
            public void onRecordFinishedListener() {
                Recording curRecording = recording;
                if (curRecording != null) {
                    // ??????????????? recording session??????????????????
                    curRecording.stop();
                    recording = null;
                    return;
                }
            }
        });
    }


    private void takePhoto() {
        if (imageCapture != null) {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                    System.currentTimeMillis() + ".jpeg");
            ImageCapture.OutputFileOptions options = (new ImageCapture.OutputFileOptions.Builder(file)).build();
            imageCapture.takePicture(options, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    uri = outputFileResults.getSavedUri();
                    if (uri == null) {
                        uri = Uri.fromFile(file);
                    }
                    path = uri.getPath();
                    index = 2;
                    setStatus(index); //??????
                    GlideUtil.create().loadLongMap(VideoRecordingActivity.this, path, mPhotoView);
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Log.e(getClass().getSimpleName(), "could not capture pic", exception);
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "camera not yet available", Toast.LENGTH_SHORT).show();
        }
    }


    public void startCamera(boolean isPreposition) {
        // ???Camera??????????????????Activity?????????????????????????????????????????????????????????????????????????????????????????????????????????
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
        cameraProviderFuture.addListener(() -> {
            try {
                // ?????????????????????????????????????????????????????????????????????
                ProcessCameraProvider processCameraProvider = cameraProviderFuture.get();
                // ????????????Preview ?????????????????????????????? surface ????????????provider??????
                Preview preview = new Preview.Builder()
                        .build();
                preview.setSurfaceProvider(mViewFinder.getSurfaceProvider());

                // ????????????????????????
                Recorder recorder = new Recorder.Builder()
                        .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                        .build();
                videoCapture = VideoCapture.withOutput(recorder);

                // ??????????????????????????????????????????
                CameraSelector cameraSelector = isPreposition ? CameraSelector.DEFAULT_BACK_CAMERA : CameraSelector.DEFAULT_FRONT_CAMERA;

                // ???????????????????????????
                imageCapture = new ImageCapture.Builder().build();

                // ?????????????????????
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .build();
                imageAnalysis.setAnalyzer(cameraExecutor, new MyAnalyzer());

                // ??????????????????????????????
                processCameraProvider.unbindAll();

                // ?????????????????????
                /*??????????????? Camera2 ??????????????????????????????????????????:
                    ?????? + ???????????? + ???????????????LIMITED??????????????????
                    ?????? + ???????????? + ???????????????LEVEL_3??????????????????????????? Android 7???N??????
                    ?????? + ???????????? + ???????????? + ???????????????????????????
                */
                processCameraProvider.bindToLifecycle(this, cameraSelector,
                        preview,
                        imageCapture/*,
                        imageAnalysis*/,
                        videoCapture);

            } catch (Exception e) {
                Log.e(TAG, "?????????????????????" + e);
            }
        }, ContextCompat.getMainExecutor(getActivity()));
    }


    public void captureVideo() {
        if (videoCapture != null) {
            // ?????????????????? recording session
            String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.SIMPLIFIED_CHINESE)
                    .format(System.currentTimeMillis());
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video");
            }
            MediaStoreOutputOptions mediaStoreOutputOptions = new MediaStoreOutputOptions
                    .Builder(getContentResolver(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    .setContentValues(contentValues)
                    .build();
            // ??????????????????
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                showMessage("?????????????????????????????????!");
                PermissionDialog.requestAudioPermissions(this, new PermissionDialog.PermissionCallBack() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure() {
                    }

                    @Override
                    public void onAlwaysFailure() {
                        PermissionDialog.showDialog(VideoRecordingActivity.this, "android.permission.RECORD_AUDIO");
                    }
                });
                return;
            }
            recording = videoCapture.getOutput().prepareRecording(getActivity(), mediaStoreOutputOptions)
                    .withAudioEnabled() // ??????????????????
                    .start(ContextCompat.getMainExecutor(getActivity()), videoRecordEvent -> {
                        if (videoRecordEvent instanceof VideoRecordEvent.Start) {
                        } else if (videoRecordEvent instanceof VideoRecordEvent.Finalize) {
                            if (!((VideoRecordEvent.Finalize) videoRecordEvent).hasError()) {
                                uri = ((VideoRecordEvent.Finalize) videoRecordEvent).getOutputResults()
                                        .getOutputUri();
                                path = VideoUtil.getLocalVideoPath(getActivity(), uri);
                                index = 1;
                                setStatus(index);   //??????
                                startPlay(path);
                            } else {
                                if (recording != null) {
                                    recording.close();
                                    recording = null;
                                    Log.e(TAG, "Video capture end with error: " +
                                            ((VideoRecordEvent.Finalize) videoRecordEvent).getError());
                                }
                            }
                        }
                    });
        }
    }


    public void startPlay(String path) {
        videoView.setVideoPath(path);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // ??????MediaPlayer??????????????????
                mp.setLooping(true);
                // OnPreparedListener??????onPrepared?????????????????????????????????????????????????????????????????????????????????
                mp.start();
            }
        });
    }

    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.iv_right, R.id.toolbar_back, R.id.tv_exit, R.id.tv_finish, R.id.iv_select_video})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_right:
                    if (isBack) {
                        isBack = false;
                        startCamera(false);
                    } else {
                        isBack = true;
                        startCamera(true);
                    }
                    break;
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_select_video:
                    chooseVideo();
                    break;
                case R.id.tv_exit: //??????
                    setCancel();
                    break;
                case R.id.tv_finish:  //??????
                    sendEvent();
                    break;
            }
        }
    }


    public void setStatus(int type) {
        mIvRight.setVisibility(View.GONE);
        mRlShoot.setVisibility(View.GONE);
        mRlOperate.setVisibility(View.VISIBLE);
        mViewFinder.setVisibility(View.GONE);
        videoView.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        mPhotoView.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
    }


    public void setCancel() {
        mIvRight.setVisibility(View.VISIBLE);
        mRlShoot.setVisibility(View.VISIBLE);
        mRlOperate.setVisibility(View.GONE);
        mViewFinder.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        mPhotoView.setVisibility(View.GONE);
    }

    private void chooseVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            //??????????????????????????????????????????
            uri = data.getData();
            if (uri.getPath().toLowerCase().contains("video") || uri.getPath().toLowerCase().contains("mp4")) {
                index = 1;
                //TODO ????????????
                path = VideoUtil.getLocalVideoPath(getActivity(), data.getData());
                if (path != null) {
                    int time = VideoUtil.getLocalVideoDuration(this, path);
                    if (time > 30) {
                        showMessage("??????????????????????????????30???");
                    } else {
                        sendEvent();
                    }
                }
            } else {
                index = 2;
                path = VideoUtil.getLocalVideoPath(getActivity(), uri);
                convertToJpg(path, path.substring(0, path.lastIndexOf('.') + 1) + "jpg");
            }
        }
    }

    /**
     * ?????????JPG???????????? ?????????????????????
     *
     * @param pngFilePath png??????bmp??????
     * @param jpgFilePath jpg??????
     */
    private void convertToJpg(String pngFilePath, String jpgFilePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(pngFilePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(jpgFilePath));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
            }
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
            bitmap = null;
        }
        //?????????JPG??????
        if (!pngFilePath.equals(jpgFilePath)) {
            File oldImg = new File(pngFilePath);
            oldImg.delete();
        }
        sendEvent();
    }


    public void sendEvent() {
        VideoEvent videoEvent = new VideoEvent();
        videoEvent.setType(index);
        videoEvent.setPath(path);
        videoEvent.setUri(uri);
        EventBus.getDefault().post(videoEvent);
        finish();
    }


    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    private static class MyAnalyzer implements ImageAnalysis.Analyzer {

        @SuppressLint("UnsafeOptInUsageError")
        @Override
        public void analyze(@NonNull ImageProxy image) {
            Log.d(TAG, "Image's stamp is " + Objects.requireNonNull(image.getImage()).getTimestamp());
            image.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

}