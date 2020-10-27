package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.reading_counter.MyApplication;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityNavigationBinding;
import com.leon.reading_counter.enums.SharedReferenceKeys;
import com.leon.reading_counter.enums.SharedReferenceNames;
import com.leon.reading_counter.utils.SharedPreferenceManager;

public class NavigationActivity extends AppCompatActivity {
    ActivityNavigationBinding binding;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext(),
                SharedReferenceNames.ACCOUNT.getValue());
        int theme = sharedPreferenceManager.getIntData(SharedReferenceKeys.THEME_STABLE.getValue());
        MyApplication.onActivitySetTheme(this, theme, true);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    void initialize() {
        initializeImageViews();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initializeImageViews() {
        binding.imageViewAccount.setImageDrawable(getDrawable(R.drawable.img_subscribe));
        binding.imageViewAddress.setImageDrawable(getDrawable(R.drawable.img_address));
        binding.imageViewCounterSerial.setImageDrawable(getDrawable(R.drawable.img_counter));
        binding.imageViewPhoneNumber.setImageDrawable(getDrawable(R.drawable.img_phone));
        binding.imageViewMobile.setImageDrawable(getDrawable(R.drawable.img_mobile));
        binding.imageViewEmpty.setImageDrawable(getDrawable(R.drawable.img_home));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.imageViewAccount.setImageDrawable(null);
        binding.imageViewAddress.setImageDrawable(null);
        binding.imageViewCounterSerial.setImageDrawable(null);
        binding.imageViewPhoneNumber.setImageDrawable(null);
        binding.imageViewMobile.setImageDrawable(null);
        binding.imageViewEmpty.setImageDrawable(null);
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}