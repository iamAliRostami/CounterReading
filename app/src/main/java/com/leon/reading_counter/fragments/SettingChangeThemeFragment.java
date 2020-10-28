package com.leon.reading_counter.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.reading_counter.R;
import com.leon.reading_counter.activities.SettingActivity;
import com.leon.reading_counter.databinding.FragmentSettingChangeThemeBinding;
import com.leon.reading_counter.enums.BundleEnum;
import com.leon.reading_counter.enums.SharedReferenceKeys;
import com.leon.reading_counter.enums.SharedReferenceNames;
import com.leon.reading_counter.infrastructure.ISharedPreferenceManager;
import com.leon.reading_counter.utils.CustomToast;
import com.leon.reading_counter.utils.SharedPreferenceManager;

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