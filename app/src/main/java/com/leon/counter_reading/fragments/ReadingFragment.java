package com.leon.counter_reading.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    ReadingData.OnOffLoadDto onOffLoadDto;
    ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto;
    FragmentReadingBinding binding;
    Context context;

    public ReadingFragment() {
    }

    public static ReadingFragment newInstance(ReadingData.OnOffLoadDto onOffLoadDto,
                                              ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto
    ) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json1 = gson.toJson(onOffLoadDto);
        String json2 = gson.toJson(readingConfigDefaultDto);
        args.putString(BundleEnum.ON_OFF_LOAD.getValue(), json1);
        args.putString(BundleEnum.READING_CONFIG_DEFAULT_DTOS.getValue(), json2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            onOffLoadDto = gson.fromJson(getArguments().getString(
                    BundleEnum.ON_OFF_LOAD.getValue()), ReadingData.OnOffLoadDto.class);
            readingConfigDefaultDto = gson.fromJson(getArguments().getString(
                    BundleEnum.READING_CONFIG_DEFAULT_DTOS.getValue()),
                    ReadingData.ReadingConfigDefaultDto.class);
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
        onButtonSubmitClickListener();
    }

    void initializeViews() {
        binding.textViewAddress.setText(onOffLoadDto.address);
        binding.textViewName.setText(onOffLoadDto.firstName.concat(" ")
                .concat(onOffLoadDto.sureName));
        binding.textViewPreDate.setText(onOffLoadDto.preDate);
        binding.textViewPreNumber.setText(String.valueOf(onOffLoadDto.preNumber));
        binding.textViewSerial.setText(onOffLoadDto.counterSerial);
        binding.textViewRadif.setText(String.valueOf(onOffLoadDto.radif));
    }

    void onButtonSubmitClickListener() {
        binding.buttonSubmit.setOnClickListener(v -> {
            Log.e("zone", readingConfigDefaultDto.zone);
        });
    }
}