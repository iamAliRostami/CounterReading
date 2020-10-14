package com.leon.reading_counter.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.leon.reading_counter.infrastructure.ISharedPreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceManager implements ISharedPreferenceManager {
    Context context;
    SharedPreferences appPrefs;

    public SharedPreferenceManager(Context context, String xml) {
        this.context = context;
        appPrefs = this.context.getSharedPreferences(xml, MODE_PRIVATE);
    }

    public boolean CheckIsNotEmpty(String key) {
        if (appPrefs == null) {
            return false;
        } else if (appPrefs.getString(key, "").length() < 1) {
            return false;
        } else return appPrefs.getString(key, "").isEmpty();
    }

    @Override
    public void putData(String key, int value) {

    }

    @Override
    public void putData(String key, String data) {
        SharedPreferences.Editor prefsEditor = appPrefs.edit();
        prefsEditor.putString(key, data);
        prefsEditor.apply();
    }

    @Override
    public void putData(String key, boolean value) {

    }

    @Override
    public String getStringData(String key) {
        return appPrefs.getString(key, "");
    }

    @Override
    public int getIntData(String key) {
        return 0;
    }

    @Override
    public boolean getBoolData(String key) {
        return false;
    }
}
