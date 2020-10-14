package com.leon.reading_counter;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {
    public static int REQUEST_LOCATION_CODE = 1236;
    static Context appContext;

    public static Context getContext() {
        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appContext = getApplicationContext();
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        appContext = getApplicationContext();
        super.onCreate();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
