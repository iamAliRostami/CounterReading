package com.leon.counter_reading.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.reading_counter.databinding.FragmentReportTotalBinding;

import org.jetbrains.annotations.NotNull;

public class ReportTotalFragment extends Fragment {
    FragmentReportTotalBinding binding;

    public ReportTotalFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportTotalBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}