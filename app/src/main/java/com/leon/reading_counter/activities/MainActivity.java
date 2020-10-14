package com.leon.reading_counter.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.reading_counter.databinding.ActivityMainBinding;
import com.leon.reading_counter.utils.CustomProgressBar;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        initialize();
    }

    void initialize() {
        CustomProgressBar progressBar = new CustomProgressBar();
        progressBar.show(context, true);
    }
}