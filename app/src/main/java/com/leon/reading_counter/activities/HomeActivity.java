package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.leon.reading_counter.MyApplication;
import com.leon.reading_counter.R;
import com.leon.reading_counter.base_items.BaseActivity;
import com.leon.reading_counter.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity {
    ActivityHomeBinding binding;
    @SuppressLint("NonConstantResourceId")
    View.OnClickListener onClickListener = v -> {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.linear_layout_download:
                MyApplication.position = 0;
                intent = new Intent(getApplicationContext(), DownloadActivity.class);
                break;
            case R.id.linear_layout_reading:
                MyApplication.position = 1;
                intent = new Intent(getApplicationContext(), ReadingActivity.class);
                break;
            case R.id.linear_layout_upload:
                MyApplication.position = 2;
                intent = new Intent(getApplicationContext(), UploadActivity.class);
                break;
            case R.id.linear_layout_report:
                MyApplication.position = 3;
                intent = new Intent(getApplicationContext(), ReadingReportActivity.class);
                break;
            case R.id.linear_layout_location:
                MyApplication.position = 4;
                intent = new Intent(getApplicationContext(), LocationActivity.class);
                break;
            case R.id.linear_layout_reading_setting:
                MyApplication.position = 5;
                intent = new Intent(getApplicationContext(), ReadingSettingActivity.class);
                break;
            case R.id.linear_layout_app_setting:
                MyApplication.position = 6;
                intent = new Intent(getApplicationContext(), SettingActivity.class);
                break;
            case R.id.linear_layout_help:
                MyApplication.position = 7;
                intent = new Intent(getApplicationContext(), HelpActivity.class);
                break;
            case R.id.linear_layout_exit:
                finishAffinity();
                break;
        }
        if (id != R.id.linear_layout_exit)
            startActivity(intent);
    };

    @Override
    protected void initialize() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        setOnImageViewClickListener();
    }

    void setOnImageViewClickListener() {
        binding.linearLayoutDownload.setOnClickListener(onClickListener);
        binding.linearLayoutReading.setOnClickListener(onClickListener);
        binding.linearLayoutUpload.setOnClickListener(onClickListener);
        binding.linearLayoutReport.setOnClickListener(onClickListener);
        binding.linearLayoutHelp.setOnClickListener(onClickListener);
        binding.linearLayoutLocation.setOnClickListener(onClickListener);
        binding.linearLayoutAppSetting.setOnClickListener(onClickListener);
        binding.linearLayoutReadingSetting.setOnClickListener(onClickListener);
        binding.linearLayoutExit.setOnClickListener(onClickListener);
    }
}