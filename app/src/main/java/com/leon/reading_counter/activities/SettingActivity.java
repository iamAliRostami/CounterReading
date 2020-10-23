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
import com.leon.reading_counter.databinding.ActivitySettingBinding;
import com.leon.reading_counter.fragments.SettingChangePasswordFragment;
import com.leon.reading_counter.fragments.SettingChangeThemeFragment;
import com.leon.reading_counter.fragments.SettingUpdateFragment;
import com.leon.reading_counter.utils.DepthPageTransformer;

public class SettingActivity extends BaseActivity {
    ActivitySettingBinding binding;
    private int previousState, currentState;

    @Override
    protected void initialize() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        setupViewPager();
        initializeTextViews();
    }

    void initializeTextViews() {
        textViewChangeTheme();
        textViewChangePassword();
        textViewUpdate();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewChangeTheme() {
        binding.textViewChangeTheme.setOnClickListener(view -> {
            setColor();
            binding.textViewChangeTheme.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(0);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewUpdate() {
        binding.textViewUpdate.setOnClickListener(view -> {
            setColor();
            binding.textViewUpdate.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(1);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void textViewChangePassword() {
        binding.textViewChangePassword.setOnClickListener(view -> {
            setColor();
            binding.textViewChangePassword.setBackground(getResources().getDrawable(R.drawable.border_white_2));
            setPadding();
            binding.viewPager.setCurrentItem(2);
        });
    }

    private void setColor() {
        binding.textViewUpdate.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewUpdate.setTextColor(getResources().getColor(R.color.text_color_light));
        binding.textViewChangeTheme.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewChangeTheme.setTextColor(getResources().getColor(R.color.text_color_light));
        binding.textViewChangePassword.setBackgroundColor(Color.TRANSPARENT);
        binding.textViewChangePassword.setTextColor(getResources().getColor(R.color.text_color_light));
    }

    private void setPadding() {
        binding.textViewChangeTheme.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
        binding.textViewUpdate.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
        binding.textViewChangePassword.setPadding(0,
                (int) getResources().getDimension(R.dimen.medium_dp), 0,
                (int) getResources().getDimension(R.dimen.medium_dp));
    }

    private void setupViewPager() {
        ViewPagerAdapterTab adapter = new ViewPagerAdapterTab(getSupportFragmentManager());
        adapter.addFragment(new SettingChangeThemeFragment(), "تغییر پوسته");
        adapter.addFragment(new SettingChangePasswordFragment(), "تغییر گذرواژه");
        adapter.addFragment(new SettingUpdateFragment(), "به روز رسانی");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.textViewChangeTheme.callOnClick();
                } else if (position == 1) {
                    binding.textViewChangePassword.callOnClick();
                } else if (position == 2) {
                    binding.textViewUpdate.callOnClick();
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