package com.leon.counter_reading.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.leon.counter_reading.databinding.FragmentReadingBinding;
import com.leon.counter_reading.enums.BundleEnum;
import com.leon.counter_reading.tables.ReadingData;

import org.jetbrains.annotations.NotNull;

public class ReadingFragment extends Fragment {
    ReadingData.OnOffLoadDto onOffLoadDtos;
    FragmentReadingBinding binding;

    public ReadingFragment() {
    }

    public static ReadingFragment newInstance(ReadingData.OnOffLoadDto onOffLoadDtos) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(onOffLoadDtos);
        args.putString(BundleEnum.ON_OFF_LOAD.getValue(), json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            onOffLoadDtos = gson.fromJson(getArguments().getString(
                    BundleEnum.ON_OFF_LOAD.getValue()), ReadingData.OnOffLoadDto.class);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReadingBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    void initialize() {
        initializeViews();
    }

    void initializeViews() {
        binding.textViewAddress.setText(onOffLoadDtos.address);
        binding.textViewName.setText(onOffLoadDtos.firstName.concat(" ")
                .concat(onOffLoadDtos.sureName));
        binding.textViewPreDate.setText(onOffLoadDtos.preDate);
        binding.textViewPreNumber.setText(String.valueOf(onOffLoadDtos.preNumber));
        binding.textViewSerial.setText(onOffLoadDtos.counterSerial);
        binding.textViewRadif.setText(String.valueOf(onOffLoadDtos.radif));

    }
}