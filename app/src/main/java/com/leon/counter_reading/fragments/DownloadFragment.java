package com.leon.counter_reading.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leon.counter_reading.R;
import com.leon.counter_reading.databinding.FragmentDownloadBinding;
import com.leon.counter_reading.enums.BundleEnum;
import com.leon.counter_reading.enums.DialogType;
import com.leon.counter_reading.enums.ProgressType;
import com.leon.counter_reading.infrastructure.IAbfaService;
import com.leon.counter_reading.infrastructure.ICallback;
import com.leon.counter_reading.infrastructure.ICallbackError;
import com.leon.counter_reading.infrastructure.ICallbackIncomplete;
import com.leon.counter_reading.tables.ReadingData;
import com.leon.counter_reading.utils.CustomDialog;
import com.leon.counter_reading.utils.CustomErrorHandling;
import com.leon.counter_reading.utils.CustomToast;
import com.leon.counter_reading.utils.HttpClientWrapper;
import com.leon.counter_reading.utils.MyDatabaseClient;
import com.leon.counter_reading.utils.NetworkHelper;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadFragment extends Fragment {
    final int[] imageSrc = {R.drawable.img_download, R.drawable.img_download_retry,
            R.drawable.img_download_off, R.drawable.img_download_special};
    FragmentDownloadBinding binding;
    int type;
    Context context;

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
        context = getActivity();
        binding.imageViewDownload.setImageResource(imageSrc[type - 1]);
        setOnButtonDownloadClickListener();
    }

    void setOnButtonDownloadClickListener() {
        binding.buttonDownload.setOnClickListener(v -> downloadType());
    }

    void downloadType() {
        if (type == 1) {
            Retrofit retrofit = NetworkHelper.getInstance();
            IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
            Call<ReadingData> call = iAbfaService.loadData();
            HttpClientWrapper.callHttpAsync(call, ProgressType.SHOW.getValue(), context,
                    new DownloadCompleted(), new DownloadIncomplete(), new DownloadError());
        }
    }

    class DownloadCompleted implements ICallback<ReadingData> {
        @Override
        public void execute(Response<ReadingData> response) {
            //TODO
            if (response != null && response.body() != null) {
                ReadingData readingData = response.body();
                MyDatabaseClient.getInstance(context).getMyDatabase().trackingDao().
                        insertAllTrackingDtos(readingData.trackingDtos);
                MyDatabaseClient.getInstance(context).getMyDatabase().counterStateDao().
                        insertAllCounterStateDto(readingData.counterStateDtos);
                MyDatabaseClient.getInstance(context).getMyDatabase().
                        karbariDao().insertAllKarbariDtos(readingData.karbariDtos);
                MyDatabaseClient.getInstance(context).getMyDatabase().
                        qotrDictionaryDao().insertQotrDictionaries(readingData.qotrDictionary);
                MyDatabaseClient.getInstance(context).getMyDatabase().
                        readingConfigDefaultDao().insertAllReadingConfigDefault(
                        readingData.readingConfigDefaultDtos);
                MyDatabaseClient.getInstance(context).getMyDatabase().onOffLoadDao().
                        insertAllOnOffLoad(readingData.onOffLoadDtos);
            }
        }
    }

    class DownloadIncomplete implements ICallbackIncomplete<ReadingData> {
        @Override
        public void executeIncomplete(Response<ReadingData> response) {
            CustomErrorHandling customErrorHandlingNew = new CustomErrorHandling(context);
            String error = customErrorHandlingNew.getErrorMessageDefault(response);
            new CustomDialog(DialogType.Yellow, context, error,
                    context.getString(R.string.dear_user),
                    context.getString(R.string.download),
                    context.getString(R.string.accepted));
        }
    }

    class DownloadError implements ICallbackError {
        @Override
        public void executeError(Throwable t) {
            CustomErrorHandling customErrorHandlingNew = new CustomErrorHandling(context);
            String error = customErrorHandlingNew.getErrorMessageTotal(t);
            CustomToast customToast = new CustomToast();
            customToast.error(error);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.imageViewDownload.setImageDrawable(null);
    }
}