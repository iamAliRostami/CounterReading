package com.leon.reading_counter.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.widget.Toast;

import com.leon.reading_counter.infrastructure.IFlashLightManager;

public final class FlashLightManager implements IFlashLightManager {
    private final Context context;
    private boolean isFlashOn = false;
    public static Camera cam = null;// has to be static, otherwise onDestroy() destroys it


    public FlashLightManager(Context context) {
        this.context = context;
    }

    public void flashLightOn() {
        try {
            if (context.getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam = Camera.open();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void flashLightOff() {
        try {
            if (context.getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void turnOn() {
        CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            boolean flashShouldBecome = true;
            String[] cameraId = camManager.getCameraIdList();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                camManager.setTorchMode(cameraId[0], flashShouldBecome);
            }
            isFlashOn = flashShouldBecome;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOff() {
        CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            boolean flashShouldBecome = false;
            String[] cameraId = camManager.getCameraIdList();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                camManager.setTorchMode(cameraId[0], flashShouldBecome);
            }
            isFlashOn = flashShouldBecome;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean toggleFlash() {
        if (isFlashOn) {
            turnOff();
            return false;
        } else {
            turnOn();
            return true;
        }
    }
}
