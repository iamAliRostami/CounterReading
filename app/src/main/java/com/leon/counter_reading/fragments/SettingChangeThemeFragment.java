package com.leon.counter_reading.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.counter_reading.activities.SettingActivity;
import com.leon.counter_reading.enums.BundleEnum;
import com.leon.counter_reading.enums.SharedReferenceKeys;
import com.leon.counter_reading.enums.SharedReferenceNames;
import com.leon.counter_reading.infrastructure.ISharedPreferenceManager;
import com.leon.counter_reading.utils.CustomToast;
import com.leon.counter_reading.utils.SharedPreferenceManager;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.FragmentSettingChangeThemeBinding;

import org.jetbrains.annotations.NotNull;

public class SettingChangeThemeFragment extends Fragment {
    FragmentSettingChangeThemeBinding binding;
    static int theme;
    ISharedPreferenceManager sharedPreferenceManager;

    public SettingChangeThemeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingChangeThemeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        sharedPreferenceManager = new SharedPreferenceManager(getActivity(), SharedReferenceNames.ACCOUNT.getValue());
        binding.imageViewBlack.setImageDrawable(getResources().getDrawable(R.drawable.img_black));
        binding.imageViewBlue.setImageDrawable(getResources().getDrawable(R.drawable.img_blue));
        binding.imageViewLightBlue.setImageDrawable(getResources().getDrawable(R.drawable.img_pale_blue));
        binding.imageViewGreen.setImageDrawable(getResources().getDrawable(R.drawable.img_green));
        binding.imageViewTheme.setImageDrawable(getResources().getDrawable(R.drawable.img_change_theme));
        setOnChangeThemeClickListener();
        setButtonChangeThemeClickListener();
    }

    void setOnChangeThemeClickListener() {
        binding.linearLayoutBlue.setOnClickListener(view -> {
            theme = 1;
            changeTheme();
        });
        binding.linearLayoutGreen.setOnClickListener(view -> {
            theme = 2;
            changeTheme();
        });
        binding.linearLayoutLightBlue.setOnClickListener(view -> {
            theme = 3;
            changeTheme();
        });
        binding.linearLayoutBlack.setOnClickListener(view -> {
            theme = 4;
            changeTheme();
        });
    }

    void setButtonChangeThemeClickListener() {
        binding.buttonChangeTheme.setOnClickListener(view -> {
            sharedPreferenceManager.putData(SharedReferenceKeys.THEME_STABLE.getValue(), theme);
            new CustomToast().success(getString(R.string.theme_changed));
            changeTheme();
        });
    }

    void changeTheme() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        intent.putExtra(BundleEnum.THEME.getValue(), theme);
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.imageViewBlack.setImageDrawable(null);
        binding.imageViewBlue.setImageDrawable(null);
        binding.imageViewLightBlue.setImageDrawable(null);
        binding.imageViewGreen.setImageDrawable(null);
        binding.imageViewTheme.setImageDrawable(null);
    }
}