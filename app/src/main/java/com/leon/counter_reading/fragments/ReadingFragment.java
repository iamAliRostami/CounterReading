package com.leon.counter_reading.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.leon.counter_reading.R;
import com.leon.counter_reading.activities.ReadingActivity;
import com.leon.counter_reading.adapters.SpinnerCustomAdapter;
import com.leon.counter_reading.databinding.FragmentReadingBinding;
import com.leon.counter_reading.enums.BundleEnum;
import com.leon.counter_reading.enums.HighLowStateEnum;
import com.leon.counter_reading.tables.ReadingData;
import com.leon.counter_reading.utils.Counting;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReadingFragment extends Fragment {
    SpinnerCustomAdapter adapter;
    ReadingData.OnOffLoadDto onOffLoadDto;
    ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto;
    ReadingData.KarbariDto karbariDto;
    ReadingData.QotrDictionary qotrDictionary;
    ArrayList<ReadingData.CounterStateDto> counterStateDtos = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    FragmentReadingBinding binding;
    int position;
    int counterStateCode;
    int counterStatePosition;
    boolean canBeEmpty, canLessThanPre;

    public ReadingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
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
        initializeSpinner();
        onButtonSubmitClickListener();
    }

    void initializeSpinner() {
        adapter = new SpinnerCustomAdapter(getActivity(), items);
        binding.spinner.setAdapter(adapter);
        if (onOffLoadDto.counterStatePosition != null)
            binding.spinner.setSelection(onOffLoadDto.counterStatePosition);
        else {
            for (int i = 0; i < counterStateDtos.size(); i++)
                if (counterStateDtos.get(i).moshtarakinId == onOffLoadDto.preCounterStateCode)
                    binding.spinner.setSelection(i);
        }
        setOnSpinnerSelectedListener();
    }

    void setOnSpinnerSelectedListener() {
        //TODO
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Log.e("selected", String.valueOf(i));
                counterStatePosition = i;
                counterStateCode = counterStateDtos.get(counterStatePosition).moshtarakinId;
                ReadingData.CounterStateDto counterStateDto = counterStateDtos.get(counterStatePosition);
                binding.editTextNumber.setEnabled(counterStateDto.canEnterNumber
                        || counterStateDto.shouldEnterNumber);
                canBeEmpty = !counterStateDto.shouldEnterNumber;
                canLessThanPre = counterStateDto.canNumberBeLessThanPre;
                if (onOffLoadDto.counterStatePosition == null ||
                        onOffLoadDto.counterStatePosition != binding.spinner.getSelectedItemPosition()) {
                    if ((counterStateDto.isTavizi || counterStateDto.isXarab) &&
                            counterStateDto.moshtarakinId != onOffLoadDto.preCounterStateCode) {
                        SerialFragment serialFragment = SerialFragment.newInstance(position,
                                counterStateDto.moshtarakinId, counterStatePosition);
                        serialFragment.show(getFragmentManager(), getString(R.string.counter_serial));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void initializeViews() {
        binding.textViewAddress.setText(onOffLoadDto.address);
        binding.textViewName.setText(onOffLoadDto.firstName.concat(" ")
                .concat(onOffLoadDto.sureName));
        binding.textViewPreDate.setText(onOffLoadDto.preDate);
        binding.textViewPreNumber.setText(String.valueOf(onOffLoadDto.preNumber));
        binding.textViewSerial.setText(onOffLoadDto.counterSerial);
        binding.textViewRadif.setText(String.valueOf(onOffLoadDto.radif));
        binding.textViewAhadAsli.setText(String.valueOf(onOffLoadDto.ahadMaskooniOrAsli));
        binding.textViewAhadForosh.setText(String.valueOf(onOffLoadDto.ahadTejariOrFari));
        binding.textViewAhadMasraf.setText(String.valueOf(onOffLoadDto.ahadSaierOrAbBaha));
        if (readingConfigDefaultDto.isOnQeraatCode)
            binding.textViewCode.setText(onOffLoadDto.qeraatCode);
        else binding.textViewCode.setText(onOffLoadDto.eshterak);

        binding.textViewKarbari.setText(karbariDto.title);
        binding.textViewBranch.setText(qotrDictionary.title);
    }

    void onButtonSubmitClickListener() {
        binding.buttonSubmit.setOnClickListener(v -> {
            if (canBeEmpty) {
                canBeEmpty();
            } else {
                canNotBeEmpty();
            }
        });
    }

    void canBeEmpty() {
        //TODO
        if (binding.editTextNumber.getText().toString().isEmpty()) {
            ((ReadingActivity) getActivity()).updateOnOffLoadWithoutCounterNumber(position,
                    counterStateCode, counterStatePosition);
        } else {
            View view = binding.editTextNumber;
            int currentNumber = Integer.parseInt(binding.editTextNumber.getText().toString());
            int use = currentNumber - onOffLoadDto.preNumber;
            if (canLessThanPre) {
                lessThanPre(currentNumber);
            } else if (!canLessThanPre && use < 0) {
                binding.editTextNumber.setError(getString(R.string.less_than_pre));
                view.requestFocus();
            }
        }
    }

    void canNotBeEmpty() {
        View view = binding.editTextNumber;
        if (binding.editTextNumber.getText().toString().isEmpty()) {
            binding.editTextNumber.setError(getString(R.string.counter_empty));
            view.requestFocus();
        } else {
            int currentNumber = Integer.parseInt(binding.editTextNumber.getText().toString());
            int use = currentNumber - onOffLoadDto.preNumber;
            if (canLessThanPre) {
                lessThanPre(currentNumber);
            } else if (!canLessThanPre && use < 0) {
                binding.editTextNumber.setError(getString(R.string.less_than_pre));
                view.requestFocus();
            } else {
                notEmpty(currentNumber);
            }
        }
    }

    void lessThanPre(int currentNumber) {
        //TODO
        ((ReadingActivity) getActivity()).updateOnOffLoadByCounterNumber(position,
                currentNumber, counterStateCode, counterStatePosition);
    }

    void notEmpty(int currentNumber) {
        FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity())
                .getSupportFragmentManager().beginTransaction();
        AreYouSureFragment areYouSureFragment;
        if (currentNumber == onOffLoadDto.preNumber) {
            areYouSureFragment = AreYouSureFragment.newInstance(
                    position, currentNumber, HighLowStateEnum.ZERO.getValue(),
                    counterStateCode, counterStatePosition);
            areYouSureFragment.show(fragmentTransaction, getString(R.string.use_out_of_range));
        } else {
            int status = Counting.checkHighLow(onOffLoadDto, karbariDto, readingConfigDefaultDto,
                    currentNumber);
            switch (status) {
                case 1:
                    areYouSureFragment = AreYouSureFragment.newInstance(
                            position, currentNumber, HighLowStateEnum.HIGH.getValue(),
                            counterStateCode, counterStatePosition);
                    areYouSureFragment.show(fragmentTransaction, getString(R.string.use_out_of_range));
                    break;
                case -1:
                    areYouSureFragment = AreYouSureFragment.newInstance(
                            position, currentNumber, HighLowStateEnum.LOW.getValue(),
                            counterStateCode, counterStatePosition);
                    areYouSureFragment.show(fragmentTransaction, getString(R.string.use_out_of_range));
                    break;
                case 0:
                    ((ReadingActivity) getActivity()).updateOnOffLoadByCounterNumber(position,
                            currentNumber, counterStateCode, counterStatePosition,
                            HighLowStateEnum.NORMAL.getValue());
                    break;
            }
        }
    }

    public static ReadingFragment newInstance(
            ReadingData.OnOffLoadDto onOffLoadDto,
            ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto,
            ReadingData.KarbariDto karbariDto,
            ReadingData.QotrDictionary qotrDictionary,
            ArrayList<ReadingData.CounterStateDto> counterStateDtos,
            ArrayList<String> items,
            int position) {
        ReadingFragment fragment = new ReadingFragment();
        fragment.setArguments(putBundle(onOffLoadDto, readingConfigDefaultDto, karbariDto,
                qotrDictionary, counterStateDtos, items, position));
        return fragment;
    }

    void getBundle() {
        if (getArguments() != null) {
            Gson gson = new Gson();
            onOffLoadDto = gson.fromJson(getArguments().getString(
                    BundleEnum.ON_OFF_LOAD.getValue()), ReadingData.OnOffLoadDto.class);
            readingConfigDefaultDto = gson.fromJson(getArguments().getString(
                    BundleEnum.READING_CONFIG.getValue()),
                    ReadingData.ReadingConfigDefaultDto.class);
            karbariDto = gson.fromJson(getArguments().getString(
                    BundleEnum.KARBARI_DICTONARY.getValue()),
                    ReadingData.KarbariDto.class);
            qotrDictionary = gson.fromJson(getArguments().getString(
                    BundleEnum.QOTR_DICTIONARY.getValue()),
                    ReadingData.QotrDictionary.class);

            items = getArguments().getStringArrayList(
                    BundleEnum.COUNTER_STATE_ADAPTER.getValue());
            ArrayList<String> json = getArguments().getStringArrayList(
                    BundleEnum.COUNTER_STATE.getValue());
            for (String s : json) {
                counterStateDtos.add(gson.fromJson(s, ReadingData.CounterStateDto.class));
            }
            position = getArguments().getInt(BundleEnum.POSITION.getValue());
        }
    }

    static Bundle putBundle(ReadingData.OnOffLoadDto onOffLoadDto,
                            ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto,
                            ReadingData.KarbariDto karbariDto,
                            ReadingData.QotrDictionary qotrDictionary,
                            ArrayList<ReadingData.CounterStateDto> counterStateDtos,
                            ArrayList<String> items,
                            int position) {
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json1 = gson.toJson(onOffLoadDto);
        args.putString(BundleEnum.ON_OFF_LOAD.getValue(), json1);
        String json2 = gson.toJson(readingConfigDefaultDto);
        args.putString(BundleEnum.READING_CONFIG.getValue(), json2);
        String json3 = gson.toJson(karbariDto);
        args.putString(BundleEnum.KARBARI_DICTONARY.getValue(), json3);
        String json4 = gson.toJson(qotrDictionary);
        args.putString(BundleEnum.QOTR_DICTIONARY.getValue(), json4);

        ArrayList<String> json5 = new ArrayList<>();
        for (ReadingData.CounterStateDto counterStateDto : counterStateDtos) {
            String json = gson.toJson(counterStateDto);
            json5.add(json);
        }
        args.putStringArrayList(BundleEnum.COUNTER_STATE.getValue(), json5);
        args.putStringArrayList(BundleEnum.COUNTER_STATE_ADAPTER.getValue(), items);
        args.putInt(BundleEnum.POSITION.getValue(), position);
        return args;
    }
}