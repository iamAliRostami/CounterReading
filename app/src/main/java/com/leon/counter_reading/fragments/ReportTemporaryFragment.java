package com.leon.counter_reading.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.leon.counter_reading.R;
import com.leon.counter_reading.adapters.SpinnerCustomAdapter;
import com.leon.counter_reading.databinding.FragmentReportTemporaryBinding;
import com.leon.counter_reading.enums.BundleEnum;
import com.leon.counter_reading.tables.CounterStateDto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReportTemporaryFragment extends Fragment {
    FragmentReportTemporaryBinding binding;
    ArrayList<CounterStateDto> counterStateDtos = new ArrayList<>();
    SpinnerCustomAdapter adapter;

    public ReportTemporaryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportTemporaryBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        binding.imageViewTemporary.setImageDrawable(getResources().getDrawable(R.drawable.img_temporary_report));
        initializeSpinner();
    }

    void initializeSpinner() {
        binding.spinner.setAdapter(adapter);
    }

    public static ReportTemporaryFragment newInstance(
            ArrayList<CounterStateDto> counterStateDtos, SpinnerCustomAdapter spinnerCustomAdapter) {
        ReportTemporaryFragment fragment = new ReportTemporaryFragment();
        fragment.setArguments(putBundle(counterStateDtos, spinnerCustomAdapter));
        return fragment;
    }

    void getBundle() {
        if (getArguments() != null) {
            Gson gson = new Gson();
            ArrayList<String> json1 = getArguments().getStringArrayList(
                    BundleEnum.COUNTER_STATE.getValue());
            for (String s : json1) {
                counterStateDtos.add(gson.fromJson(s, CounterStateDto.class));
            }
            adapter = (SpinnerCustomAdapter) getArguments().getSerializable(
                    BundleEnum.Item.getValue());
        }
    }

    static Bundle putBundle(ArrayList<CounterStateDto> counterStateDtos,
                            SpinnerCustomAdapter spinnerCustomAdapter) {
        Bundle args = new Bundle();
        Gson gson = new Gson();
        ArrayList<String> json1 = new ArrayList<>();
        for (CounterStateDto counterStateDto : counterStateDtos) {
            String jsonTemp = gson.toJson(counterStateDto);
            json1.add(jsonTemp);
        }
        args.putStringArrayList(BundleEnum.COUNTER_STATE.getValue(), json1);

        args.putSerializable(BundleEnum.Item.getValue(), spinnerCustomAdapter);
        return args;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.imageViewTemporary.setImageDrawable(null);
    }
}