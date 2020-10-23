package com.leon.reading_counter.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.FragmentSettingChangeThemeBinding;

import org.jetbrains.annotations.NotNull;

public class SettingChangeThemeFragment extends Fragment {
    FragmentSettingChangeThemeBinding binding;

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
        binding.imageViewBlack.setImageDrawable(getResources().getDrawable(R.drawable.img_black));
        binding.imageViewBlue.setImageDrawable(getResources().getDrawable(R.drawable.img_blue));
        binding.imageViewLightBlue.setImageDrawable(getResources().getDrawable(R.drawable.img_pale_blue));
        binding.imageViewGreen.setImageDrawable(getResources().getDrawable(R.drawable.img_green));
        binding.imageViewTheme.setImageDrawable(getResources().getDrawable(R.drawable.img_change_theme));
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