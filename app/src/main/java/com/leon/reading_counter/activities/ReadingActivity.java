package com.leon.reading_counter.activities;

import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.leon.reading_counter.R;
import com.leon.reading_counter.adapters.ViewPagerAdapterReading;
import com.leon.reading_counter.base_items.BaseActivity;
import com.leon.reading_counter.databinding.ActivityReadingBinding;
import com.leon.reading_counter.infrastructure.IFlashLightManager;
import com.leon.reading_counter.utils.DepthPageTransformer;
import com.leon.reading_counter.utils.FlashLightManager;

import java.util.ArrayList;

public class ReadingActivity extends BaseActivity {
    ActivityReadingBinding binding;
    private boolean isFlashOn = false;
    private IFlashLightManager flashLightManager;
    private int previousState, currentState;

    @Override
    protected void initialize() {
        binding = ActivityReadingBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        setupViewPager();
    }

    void setOnImageViewsClickListener() {
        flashLightManager = new FlashLightManager(getApplicationContext());
        ImageView imageViewFlash = findViewById(R.id.image_view_flash);
        imageViewFlash.setOnClickListener(v -> {
            if (isFlashOn) {
//                flashLightManager.turnOff();
                flashLightManager.flashLightOff();
            } else {
                flashLightManager.flashLightOn();
//                flashLightManager.turnOn();
            }
            isFlashOn = !isFlashOn;
        });
    }

    private void setupViewPager() {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i <= 4000; i++)
            ints.add(i);
        ViewPagerAdapterReading adapter = new ViewPagerAdapterReading(getSupportFragmentManager(), ints);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.viewPager.setPageTransformer(true, new DepthPageTransformer());
    }
}