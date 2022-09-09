package com.drifting.bureau.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.google.ar.sceneform.ArSceneView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class TakePhoto {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void takePhoto(ArSceneView arFragmentSceneView, Context context, RelativeLayout main_content) {
        final String filename = generateFilename();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(arFragmentSceneView.getWidth(), arFragmentSceneView.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(arFragmentSceneView, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename, context);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(context, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
//                Snackbar snackbar = Snackbar.make(arFragmentSceneView,
//                        "Photo saved", Snackbar.LENGTH_LONG);
//                snackbar.setAction("Open in Photos", v -> {
//                    File photoFile = new File(filename);
//
//                    Uri photoURI = FileProvider.getUriForFile(context,
//                            context.getPackageName() + ".ar.codelab.name.provider",
//                            photoFile);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
//                    intent.setDataAndType(photoURI, "image/*");
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    context.startActivity(intent);
//
//                });
//                snackbar.show();
            } else {
                Toast toast = Toast.makeText(context,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

    private void saveBitmapToDisk(Bitmap bitmap, String filename, Context context) throws IOException {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        shareIntent = Intent.createChooser(shareIntent, "Share");
        context.startActivity(shareIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String generateFilename() {
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "Sceneform/" + date + "_screenshot.jpg";
    }
}
