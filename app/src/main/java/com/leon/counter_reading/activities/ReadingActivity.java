package com.leon.counter_reading.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.leon.counter_reading.MyApplication;
import com.leon.counter_reading.R;
import com.leon.counter_reading.adapters.ViewPagerAdapterReading;
import com.leon.counter_reading.base_items.BaseActivity;
import com.leon.counter_reading.databinding.ActivityReadingBinding;
import com.leon.counter_reading.fragments.SearchFragment;
import com.leon.counter_reading.infrastructure.IFlashLightManager;
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
    private boolean isFlashOn = false, isNight = false;
    private IFlashLightManager flashLightManager;
    private int previousState, currentState;

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

    private void setupViewPager() {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i <= 4000; i++)
            ints.add(i);
        ViewPagerAdapterReading adapter = new ViewPagerAdapterReading(getSupportFragmentManager(), ints);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    void checkPermissions() {
        if (PermissionManager.gpsEnabled(this))
            if (!PermissionManager.checkLocationPermission(getApplicationContext())) {
                askLocationPermission();
            } else if (!PermissionManager.checkStoragePermission(getApplicationContext())) {
                askStoragePermission();
            } else {
                gpsTracker = new GPSTracker(activity);
                setupViewPager();
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