package com.leon.counter_reading.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Debug;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.leon.counter_reading.adapters.ViewPagerAdapterTab;
import com.leon.counter_reading.base_items.BaseActivity;
import com.leon.counter_reading.fragments.ReportNotReadingFragment;
import com.leon.counter_reading.fragments.ReportTemporaryFragment;
import com.leon.counter_reading.fragments.ReportTotalFragment;
import com.leon.counter_reading.utils.DepthPageTransformer;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityReportBinding;

public class ReportActivity extends BaseActivity {
    ActivityReportBinding binding;
    private int previousState, currentState;

    @Override
    protected void initialize() {
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        setupViewPager();
        initializeTextViews();
    }

    void initializeTextViews() {
        textViewTotalNormal();
        textViewTemporary();
        textViewNotRead();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewTotalNormal() {
        binding.textViewTotal.setOnClickListener(view -> {
            setColor();
            binding.textViewTotal.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(0);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewNotRead() {
        binding.textViewNotRead.setOnClickListener(view -> {
            setColor();
            binding.textViewNotRead.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(1);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewTemporary() {
        binding.textViewTemporary.setOnClickListener(view -> {
            setColor();
            binding.textViewTemporary.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(2);
        });
    }


    private void setColor() {
        binding.textViewNotRead.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewNotRead.setTextColor(getResources().getColor(R.color.text_color_light));
        binding.textViewTotal.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewTotal.setTextColor(getResources().getColor(R.color.text_color_light));
        binding.textViewTemporary.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewTemporary.setTextColor(getResources().getColor(R.color.text_color_light));
    }

    private void setPadding() {
        binding.textViewTotal.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
        binding.textViewNotRead.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
        binding.textViewTemporary.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
    }

    private void setupViewPager() {
        //TODO
        ViewPagerAdapterTab adapter = new ViewPagerAdapterTab(getSupportFragmentManager());
        adapter.addFragment(new ReportTotalFragment(), "آمار کلی");
        adapter.addFragment(new ReportNotReadingFragment(), "قرائت نشده");
        adapter.addFragment(new ReportTemporaryFragment(), "علی الحساب");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.textViewTotal.callOnClick();
                } else if (position == 1) {
                    binding.textViewNotRead.callOnClick();
                } else if (position == 2) {
                    binding.textViewTemporary.callOnClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int currentPage = binding.viewPager.getCurrentItem();
                if (currentPage == 2 || currentPage == 0) {
                    previousState = currentState;
                    currentState = state;
                    if (previousState == 1 && currentState == 0) {
                        binding.viewPager.setCurrentItem(currentPage == 0 ? 2 : 0);
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