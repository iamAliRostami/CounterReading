package com.leon.counter_reading.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.reading_counter.databinding.FragmentReadingSettingBinding;

import org.jetbrains.annotations.NotNull;

public class ReadingSettingFragment extends Fragment {
    FragmentReadingSettingBinding binding;

    public ReadingSettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReadingSettingBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    void initialize() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}