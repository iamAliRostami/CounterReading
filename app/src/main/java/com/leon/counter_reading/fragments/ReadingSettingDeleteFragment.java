package com.leon.counter_reading.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.counter_reading.R;
import com.leon.counter_reading.databinding.FragmentReadingSettingDeleteBinding;

import org.jetbrains.annotations.NotNull;

public class ReadingSettingDeleteFragment extends Fragment {
    FragmentReadingSettingDeleteBinding binding;

    public ReadingSettingDeleteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReadingSettingDeleteBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        binding.imageViewDelete.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.imageViewDelete.setImageDrawable(null);
    }
}