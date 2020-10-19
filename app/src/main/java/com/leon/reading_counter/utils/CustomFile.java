package com.leon.reading_counter.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import com.leon.reading_counter.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CustomFile {

    @SuppressLint("SimpleDateFormat")
    public static MultipartBody.Part bitmapToFile(Bitmap bitmap, Context context) {
        String timeStamp = (new SimpleDateFormat(context.getString(R.string.save_format_name))).format(new Date());
        String fileNameToSave = "JPEG_" + timeStamp + "_";
        File f = new File(context.getCacheDir(), fileNameToSave);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();
        //write the bytes in file
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), f);
        return MultipartBody.Part.createFormData("imageFile", f.getName(), reqFile);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public void saveTempBitmap(Bitmap bitmap, Context context) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap, context);
        } else {
            Log.e("error", "ExternalStorage is not Writable");
            new CustomToast().warning(context.getString(R.string.error_external_storage_is_not_writable));
        }
    }

    @SuppressLint("SimpleDateFormat")
    void saveImage(Bitmap bitmapImage, Context context) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + context.getString(R.string.camera_folder));
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        }
        String timeStamp = (new SimpleDateFormat(context.getString(R.string.save_format_name))).format(new Date());
        String fileNameToSave = "JPEG_" + timeStamp + "_";
        File file = new File(mediaStorageDir, fileNameToSave);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", Objects.requireNonNull(e.getMessage()));
        }
        MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
    }
}
