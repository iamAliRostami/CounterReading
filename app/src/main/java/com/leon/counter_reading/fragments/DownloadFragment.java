package com.leon.counter_reading.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.counter_reading.enums.BundleEnum;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.FragmentDownloadBinding;

import org.jetbrains.annotations.NotNull;

public class DownloadFragment extends Fragment {
    final int[] imageSrc = {R.drawable.img_download, R.drawable.img_download_retry,
            R.drawable.img_download_off, R.drawable.img_download_special};
    FragmentDownloadBinding binding;
    int type;

    public DownloadFragment() {
    }

    public static DownloadFragment newInstance(int type) {
        DownloadFragment fragment = new DownloadFragment();
        Bundle args = new Bundle();
        args.putInt(BundleEnum.TYPE.getValue(), type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(BundleEnum.TYPE.getValue());
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDownloadBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    void initialize() {
        binding.imageViewDownload.setImageResource(imageSrc[type - 1]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.imageViewDownload.setImageDrawable(null);
    }
}