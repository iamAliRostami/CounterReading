package com.leon.counter_reading.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Debug;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.leon.counter_reading.MyApplication;
import com.leon.counter_reading.R;
import com.leon.counter_reading.adapters.ViewPagerAdapterReading;
import com.leon.counter_reading.base_items.BaseActivity;
import com.leon.counter_reading.databinding.ActivityReadingBinding;
import com.leon.counter_reading.enums.OffloadStateEnum;
import com.leon.counter_reading.fragments.SearchFragment;
import com.leon.counter_reading.infrastructure.IFlashLightManager;
import com.leon.counter_reading.tables.ReadingData;
import com.leon.counter_reading.utils.CustomFile;
import com.leon.counter_reading.utils.CustomToast;
import com.leon.counter_reading.utils.DepthPageTransformer;
import com.leon.counter_reading.utils.FlashLightManager;
import com.leon.counter_reading.utils.GPSTracker;
import com.leon.counter_reading.utils.PermissionManager;

import java.util.ArrayList;

import static com.leon.counter_reading.utils.PermissionManager.isNetworkAvailable;

public class ReadingActivity extends BaseActivity {
    ActivityReadingBinding binding;
    Activity activity;
    GPSTracker gpsTracker;
    IFlashLightManager flashLightManager;
    boolean isFlashOn = false, isNight = false;
    final int[] imageSrc = new int[12];
    boolean[] currentRead;
    ReadingData readingData;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void initialize() {
        binding = ActivityReadingBinding.inflate(getLayoutInflater());
        View childLayout = binding.getRoot();
        ConstraintLayout parentLayout = findViewById(R.id.base_Content);
        parentLayout.addView(childLayout);
        activity = this;
        if (isNetworkAvailable(getApplicationContext()))
            checkPermissions();
        else PermissionManager.enableNetwork(this);
    }

    public void updateOnOffLoadByCounterNumber(int position, int type, int number) {
        //TODO
        readingData.onOffLoadDtos.get(position).isBazdid = true;
        readingData.onOffLoadDtos.get(position).highLowStateId = type;
        readingData.onOffLoadDtos.get(position).offLoadStateId = OffloadStateEnum.INSERTED.getValue();
        readingData.onOffLoadDtos.get(position).counterNumber = number;
        attemptSend(position);
        if (binding.viewPager.getCurrentItem() < readingData.onOffLoadDtos.size())
            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
    }

    void attemptSend(int position) {
        //TODO
        currentRead[binding.viewPager.getCurrentItem()] = true;
        setAboveIconsSrc(position);
    }

    void setAboveIconsSrc(int position) {
        runOnUiThread(() -> {
            setIsBazdidImage(position);
            setHighLowImage(position);
            setReadStatusImage(position);
        });
    }

    void setIsBazdidImage(int position) {
        if (readingData.onOffLoadDtos.get(position).isBazdid)
            binding.imageViewReadingType.setImageResource(imageSrc[6]);
        else binding.imageViewReadingType.setImageResource(imageSrc[7]);
    }

    void setReadStatusImage(int position) {
        binding.imageViewOffLoadState.setImageResource(
                imageSrc[readingData.onOffLoadDtos.get(position).offLoadStateId]);
        if (readingData.onOffLoadDtos.get(position).offLoadStateId == 0)
            binding.imageViewOffLoadState.setImageResource(imageSrc[8]);
    }

    void setHighLowImage(int position) {
        binding.imageViewHighLowState.setImageResource(
                imageSrc[readingData.onOffLoadDtos.get(position).highLowStateId]);
    }

    void setOnImageViewsClickListener() {
        flashLightManager = new FlashLightManager(getApplicationContext());
        ImageView imageViewFlash = findViewById(R.id.image_view_flash);
        imageViewFlash.setOnClickListener(v -> {
            if (isFlashOn) {
                isFlashOn = flashLightManager.turnOff();
            } else {
                isFlashOn = flashLightManager.turnOn();
            }
        });
        ImageView imageViewReverse = findViewById(R.id.image_view_reverse);
        imageViewReverse.setOnClickListener(v -> {
            if (!isNight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            isNight = !isNight;
        });
        ImageView imageViewCamera = findViewById(R.id.image_view_camera);
        imageViewCamera.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TakePhotoActivity.class);
            startActivity(intent);
        });
        ImageView imageViewSearch = findViewById(R.id.image_view_search);
        imageViewSearch.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            SearchFragment searchFragment = SearchFragment.newInstance("", "");
            searchFragment.show(fragmentTransaction, "");
        });
        ImageView imageViewCheck = findViewById(R.id.image_view_reading_report);
        imageViewCheck.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReadingReportActivity.class);
            startActivity(intent);
        });
    }

    void setAboveIcons() {
        imageSrc[0] = R.drawable.img_default_level;
        imageSrc[1] = R.drawable.img_normal_level;
        imageSrc[2] = R.drawable.img_high_level;
        imageSrc[3] = R.drawable.img_low_level;
        imageSrc[4] = R.drawable.img_low_level;
        imageSrc[5] = R.drawable.img_visit_default;
        imageSrc[6] = R.drawable.img_visit;
        imageSrc[7] = R.drawable.img_writing;
        imageSrc[8] = R.drawable.img_successful_default;
        imageSrc[9] = R.drawable.img_successful;
        imageSrc[10] = R.drawable.img_mistake;
        imageSrc[11] = R.drawable.img_failure;
    }

    @SuppressLint("StaticFieldLeak")
    class getDBData extends AsyncTask<Integer, Integer, Integer> {
        ProgressDialog dialog;

        public getDBData() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(activity);
            dialog.setMessage(getString(R.string.loading_getting_info));
            dialog.setTitle(getString(R.string.loading_connecting));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            dialog.dismiss();
            super.onPostExecute(integer);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            //TODO
            readingData = CustomFile.readData();
            if (readingData.onOffLoadDtos != null && readingData.onOffLoadDtos.size() > 0) {
                currentRead = new boolean[readingData.onOffLoadDtos.size()];
                runOnUiThread(ReadingActivity.this::setupViewPager);
                setAboveIconsSrc(0);
            }
            return null;
        }
    }

    void setOnPageChangeListener() {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                final String number = (position + 1) + "/" + readingData.onOffLoadDtos.size();
                runOnUiThread(() -> binding.textViewPageNumber.setText(number));
                setAboveIconsSrc(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void setupViewPager() {
        ViewPagerAdapterReading viewPagerAdapterReading =
                new ViewPagerAdapterReading(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                        readingData);
        binding.viewPager.setAdapter(viewPagerAdapterReading);
        binding.viewPager.setPageTransformer(true, new DepthPageTransformer());
        setOnPageChangeListener();
    }

    void checkPermissions() {
        if (PermissionManager.gpsEnabled(this))
            if (!PermissionManager.checkLocationPermission(getApplicationContext())) {
                askLocationPermission();
            } else if (!PermissionManager.checkStoragePermission(getApplicationContext())) {
                askStoragePermission();
            } else {
                gpsTracker = new GPSTracker(activity);
                setAboveIcons();
                new getDBData().execute();
                setOnImageViewsClickListener();
            }
    }

    void askStoragePermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                CustomToast customToast = new CustomToast();
                customToast.info(getString(R.string.access_granted));
                checkPermissions();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                PermissionManager.forceClose(activity);
            }
        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage(getString(R.string.confirm_permission))
                .setRationaleConfirmText(getString(R.string.allow_permission))
                .setDeniedMessage(getString(R.string.if_reject_permission))
                .setDeniedCloseButtonText(getString(R.string.close))
                .setGotoSettingButtonText(getString(R.string.allow_permission))
                .setPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).check();
    }

    void askLocationPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                CustomToast customToast = new CustomToast();
                customToast.info(getString(R.string.access_granted));
                checkPermissions();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                PermissionManager.forceClose(activity);
            }
        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage(getString(R.string.confirm_permission))
                .setRationaleConfirmText(getString(R.string.allow_permission))
                .setDeniedMessage(getString(R.string.if_reject_permission))
                .setDeniedCloseButtonText(getString(R.string.close))
                .setGotoSettingButtonText(getString(R.string.allow_permission))
                .setPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == MyApplication.GPS_CODE)
                checkPermissions();
            if (requestCode == MyApplication.REQUEST_NETWORK_CODE) {
                if (isNetworkAvailable(getApplicationContext()))
                    checkPermissions();
                else PermissionManager.enableNetwork(this);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gpsTracker.onBind(getIntent());
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}