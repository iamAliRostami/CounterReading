package com.leon.reading_counter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.reading_counter.databinding.FragmentLocationBinding;

import org.jetbrains.annotations.NotNull;

public class LocationFragment extends Fragment {
    FragmentLocationBinding binding;

    public LocationFragment() {
    }

    public static LocationFragment newInstance(String param1, String param2) {
        return new LocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
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