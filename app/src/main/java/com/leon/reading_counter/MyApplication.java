package com.leon.reading_counter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import androidx.multidex.MultiDex;

import es.dmoral.toasty.Toasty;

public class MyApplication extends Application {
    public static final String FONT_NAME = "font/font_1.ttf";
    public static final int REQUEST_NETWORK_CODE = 1234;
    public static final int CAMERA_REQUEST = 1888;
    public static final int GALLERY_REQUEST = 1889;
    public static final int GPS_CODE = 1235;
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;
    public static final long MIN_TIME_BW_UPDATES = 1000;
    public static final int TOAST_TEXT_SIZE = 20;
    public static int POSITION = -1;

    public static Bitmap bitmapSelectedImage;
    public static String fileName;
    static Context appContext;

    public static Context getContext() {
        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        appContext = getApplicationContext();
        Toasty.Config.getInstance()
                .tintIcon(true)
                .setToastTypeface(Typeface.createFromAsset(appContext.getAssets(), MyApplication.FONT_NAME))
                .setTextSize(TOAST_TEXT_SIZE)
                .allowQueue(true).apply();
        super.onCreate();
    }


    public static void onActivitySetTheme(Activity activity, int theme, boolean actionBar) {
        if (!actionBar) {
            if (theme == 1) {
                activity.setTheme(R.style.AppTheme_NoActionBar);
            } else if (theme == 2) {
                activity.setTheme(R.style.AppTheme_GreenBlue_NoActionBar);
            } else if (theme == 3) {
                activity.setTheme(R.style.AppTheme_Indigo_NoActionBar);
            } else if (theme == 4) {
                activity.setTheme(R.style.AppTheme_DarkGrey_NoActionBar);
            }
        } else {
            if (theme == 1) {
                activity.setTheme(R.style.AppTheme);
            } else if (theme == 2) {
                activity.setTheme(R.style.AppTheme_GreenBlue);
            } else if (theme == 3) {
                activity.setTheme(R.style.AppTheme_Indigo);
            } else if (theme == 4) {
                activity.setTheme(R.style.AppTheme_DarkGrey);
            }
        }
    }
}
