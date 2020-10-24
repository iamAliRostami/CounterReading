package com.leon.reading_counter.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.FragmentReportNotReadingBinding;

import org.jetbrains.annotations.NotNull;

public class ReportNotReadingFragment extends Fragment {
    FragmentReportNotReadingBinding binding;

    public ReportNotReadingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportNotReadingBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        binding.imageViewNotRead.setImageDrawable(getResources().getDrawable(R.drawable.img_not_read));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.imageViewNotRead.setImageDrawable(null);
    }
}