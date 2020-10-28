package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityTakePhotoBinding;

public class TakePhotoActivity extends AppCompatActivity {
    ActivityTakePhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTakePhotoBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_take_photo);
        initialize();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
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
        binding.imageView1.setImageDrawable(null);
        binding.imageView2.setImageDrawable(null);
        binding.imageView3.setImageDrawable(null);
        binding.imageView4.setImageDrawable(null);
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}