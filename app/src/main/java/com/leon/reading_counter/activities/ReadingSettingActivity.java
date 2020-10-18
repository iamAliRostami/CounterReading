package com.leon.reading_counter.activities;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.leon.reading_counter.R;
import com.leon.reading_counter.base_items.BaseActivity;
import com.leon.reading_counter.databinding.ActivityReadingSettingBinding;

public class ReadingSettingActivity extends BaseActivity {
    ActivityReadingSettingBinding binding;

    @Override
    protected void initialize() {
        binding = ActivityReadingSettingBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
    }
}