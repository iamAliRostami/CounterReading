package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityDistributionBillBinding;

public class DistributionBillActivity extends AppCompatActivity {
    ActivityDistributionBillBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDistributionBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        binding.imageViewBill.setImageDrawable(getDrawable(R.drawable.img_temporary));
    }

    private void startAnimationOnTextViewCounter() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        binding.textViewCounter.startAnimation(anim);
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
        binding.imageViewBill.setImageDrawable(null);
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}