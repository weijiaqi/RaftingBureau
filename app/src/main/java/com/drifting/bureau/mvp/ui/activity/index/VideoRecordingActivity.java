package com.drifting.bureau.mvp.ui.activity.index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.view.CircleProgressButtonView;
import com.google.common.util.concurrent.ListenableFuture;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
=======
import com.jess.arms.base.BaseActivity;
>>>>>>> origin/dev
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.picture.photoview.PhotoView;


/**
 * @Description: 视频录制
 * @Author : WeiJiaQI
 * @Time : 2022/5/16 11:49
 */
<<<<<<< HEAD
public class VideoRecordingActivity extends BaseManagerActivity<VideoRecordingPresenter> implements VideoRecordingContract.View {
=======
public class VideoRecordingActivity extends BaseActivity<VideoRecordingPresenter> implements VideoRecordingContract.View {
>>>>>>> origin/dev
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
        DaggerVideoRecordingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_recording; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
                takePhoto(); // 拍照
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
                    // 停止当前的 recording session（录制会话）
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
                    setStatus(index); //拍照
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
        // 将Camera的生命周期和Activity绑定在一起（设定生命周期所有者），这样就不用手动控制相机的启动和关闭。
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
        cameraProviderFuture.addListener(() -> {
            try {
                // 将你的相机和当前生命周期的所有者绑定所需的对象
                ProcessCameraProvider processCameraProvider = cameraProviderFuture.get();
                // 创建一个Preview 实例，并设置该实例的 surface 提供者（provider）。
                Preview preview = new Preview.Builder()
                        .build();
                preview.setSurfaceProvider(mViewFinder.getSurfaceProvider());

                // 创建录像所需实例
                Recorder recorder = new Recorder.Builder()
                        .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                        .build();
                videoCapture = VideoCapture.withOutput(recorder);

                // 选择后置摄像头作为默认摄像头
                CameraSelector cameraSelector = isPreposition ? CameraSelector.DEFAULT_BACK_CAMERA : CameraSelector.DEFAULT_FRONT_CAMERA;

                // 创建拍照所需的实例
                imageCapture = new ImageCapture.Builder().build();

                // 设置预览帧分析
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .build();
                imageAnalysis.setAnalyzer(cameraExecutor, new MyAnalyzer());

                // 重新绑定用例前先解绑
                processCameraProvider.unbindAll();

                // 绑定用例至相机
                /*此步骤具有 Camera2 设备文档中指定的设备级别要求:
                    预览 + 视频捕获 + 图像捕获：LIMITED设备及以上。
                    预览 + 视频捕获 + 图像分析：LEVEL_3（最高）设备添加到 Android 7（N）。
                    预览 + 视频捕获 + 图像分析 + 图像捕获：不支持。
                */
                processCameraProvider.bindToLifecycle(this, cameraSelector,
                        preview,
                        imageCapture/*,
                        imageAnalysis*/,
                        videoCapture);

            } catch (Exception e) {
                Log.e(TAG, "用例绑定失败！" + e);
            }
        }, ContextCompat.getMainExecutor(getActivity()));
    }


    public void captureVideo() {
        if (videoCapture != null) {
            // 创建一个新的 recording session
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
            // 申请音频权限
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                showMessage("检测到你未开启录音权限!");
                return;
            }
            recording = videoCapture.getOutput().prepareRecording(getActivity(), mediaStoreOutputOptions)
                    .withAudioEnabled() // 开启音频录制
                    .start(ContextCompat.getMainExecutor(getActivity()), videoRecordEvent -> {
                        if (videoRecordEvent instanceof VideoRecordEvent.Start) {
                        } else if (videoRecordEvent instanceof VideoRecordEvent.Finalize) {
                            if (!((VideoRecordEvent.Finalize) videoRecordEvent).hasError()) {
                                uri = ((VideoRecordEvent.Finalize) videoRecordEvent).getOutputResults()
                                        .getOutputUri();
                                path = VideoUtil.getLocalVideoPath(getActivity(), uri);
                                index = 1;
                                setStatus(index);   //视频
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
                // 通过MediaPlayer设置循环播放
                mp.setLooping(true);
                // OnPreparedListener中的onPrepared方法是在播放源准备完成后回调的，所以可以在这里开启播放
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
                case R.id.tv_exit: //取消
                    setCancel();
                    break;
                case R.id.tv_finish:  //完成
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
            //判断用户选择的是照片还是视频
            uri = data.getData();
            if (uri.getPath().toLowerCase().contains("video") || uri.getPath().toLowerCase().contains("mp4")) {
                index = 1;
                //TODO 显示视频
                path = VideoUtil.getLocalVideoPath(getActivity(), data.getData());
                if (path != null) {
                    int time = VideoUtil.getLocalVideoDuration(path);
                    if (time > 30) {
                        showMessage("视频选择时长不能超过30秒");
                    } else {
                        sendEvent();
                    }
                }
            } else {
                index = 2;
                path = VideoUtil.getLocalVideoPath(getActivity(), data.getData());
                sendEvent();
            }
        }
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