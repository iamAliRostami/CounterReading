package com.leon.counter_reading.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Debug;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.leon.counter_reading.adapters.ViewPagerAdapterTab;
import com.leon.counter_reading.base_items.BaseActivity;
import com.leon.counter_reading.fragments.ReadingSettingDeleteFragment;
import com.leon.counter_reading.fragments.ReadingSettingFragment;
import com.leon.counter_reading.utils.DepthPageTransformer;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityReadingSettingBinding;

public class ReadingSettingActivity extends BaseActivity {
    ActivityReadingSettingBinding binding;
    private int previousState, currentState;

    @Override
    protected void initialize() {
        binding = ActivityReadingSettingBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        setupViewPager();
        initializeTextViews();
    }

    void initializeTextViews() {
        textViewRead();
        textViewDelete();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewDelete() {
        binding.textViewDelete.setOnClickListener(view -> {
            setColor();
            binding.textViewDelete.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(1);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewRead() {
        binding.textViewRead.setOnClickListener(view -> {
            setColor();
            binding.textViewRead.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(0);
        });
    }

    private void setColor() {
        binding.textViewRead.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewRead.setTextColor(getResources().getColor(R.color.text_color_light));
        binding.textViewDelete.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewDelete.setTextColor(getResources().getColor(R.color.text_color_light));
    }

    private void setPadding() {
        binding.textViewRead.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
        binding.textViewDelete.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
    }

    private void setupViewPager() {
        ViewPagerAdapterTab adapter = new ViewPagerAdapterTab(getSupportFragmentManager());
        adapter.addFragment(new ReadingSettingFragment(), "تنظیمات قرائت");
        adapter.addFragment(new ReadingSettingDeleteFragment(), "حذف");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.textViewRead.callOnClick();
                } else if (position == 1) {
                    binding.textViewDelete.callOnClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int currentPage = binding.viewPager.getCurrentItem();
                if (currentPage == 1 || currentPage == 0) {
                    previousState = currentState;
                    currentState = state;
                    if (previousState == 1 && currentState == 0) {
                        binding.viewPager.setCurrentItem(currentPage == 0 ? 1 : 0);
                    }
                }
            }
        });
        binding.viewPager.setPageTransformer(true, new DepthPageTransformer());
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
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}