package com.leon.reading_counter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;

import androidx.multidex.MultiDex;

import es.dmoral.toasty.Toasty;

public class MyApplication extends Application {
    public static final String fontName = "font/font_1.ttf";
    public static int REQUEST_LOCATION_CODE = 1236;
    public static int REQUEST_NETWORK_CODE = 1234;
    public static int TOAST_TEXT_SIZE = 20;
    public static int position = -1;
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
                .setToastTypeface(Typeface.createFromAsset(appContext.getAssets(), MyApplication.fontName))
                .setTextSize(TOAST_TEXT_SIZE)
                .allowQueue(true).apply();
        super.onCreate();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void onActivitySetTheme(Activity activity, int theme) {
        if (theme == 1) {
            activity.setTheme(R.style.AppTheme_NoActionBar);
        } else if (theme == 2) {
            activity.setTheme(R.style.AppTheme_NoActionBar_GreenBlue);
        } else if (theme == 3) {
            activity.setTheme(R.style.AppTheme_NoActionBar_Indigo);
        } else if (theme == 4) {
            activity.setTheme(R.style.AppTheme_NoActionBar_DarkGrey);
        }
    }
}
