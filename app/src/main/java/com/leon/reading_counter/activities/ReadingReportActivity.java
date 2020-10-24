package com.leon.reading_counter.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.reading_counter.databinding.ActivityReadingReportBinding;

public class ReadingReportActivity extends AppCompatActivity {

    ActivityReadingReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadingReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    void initialize() {
    }
}