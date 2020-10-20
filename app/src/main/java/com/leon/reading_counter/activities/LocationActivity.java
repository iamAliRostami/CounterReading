package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Debug;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.leon.reading_counter.R;
import com.leon.reading_counter.adapters.ViewPagerAdapterTab;
import com.leon.reading_counter.base_items.BaseActivity;
import com.leon.reading_counter.databinding.ActivityLocationBinding;
import com.leon.reading_counter.fragments.LocationFragment;
import com.leon.reading_counter.fragments.PlaceFragment;
import com.leon.reading_counter.utils.DepthPageTransformer;

public class LocationActivity extends BaseActivity {
    ActivityLocationBinding binding;
    private int previousState, currentState;

    @Override
    protected void initialize() {
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        setupViewPager();
        initializeTextViews();
    }

    void initializeTextViews() {
        textViewLocation();
        textViewPlace();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewPlace() {
        binding.textViewPlace.setOnClickListener(view -> {
            setColor();
            binding.textViewPlace.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(3);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewLocation() {
        binding.textViewLocation.setOnClickListener(view -> {
            setColor();
            binding.textViewLocation.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(0);
        });
    }

    private void setColor() {
        binding.textViewLocation.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewLocation.setTextColor(getResources().getColor(R.color.text_color_light));
        binding.textViewPlace.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewPlace.setTextColor(getResources().getColor(R.color.text_color_light));
    }

    private void setPadding() {
        binding.textViewLocation.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
        binding.textViewPlace.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
    }

    private void setupViewPager() {
        ViewPagerAdapterTab adapter = new ViewPagerAdapterTab(getSupportFragmentManager());
//        if (isGisBuild()) {
        adapter.addFragment(new LocationFragment(), "لایه ها ");
//        }
        adapter.addFragment(new PlaceFragment(), "مکان کنتور");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.textViewLocation.callOnClick();
                } else if (position == 1) {
                    binding.textViewPlace.callOnClick();
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