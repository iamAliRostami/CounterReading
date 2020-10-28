package com.leon.reading_counter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.leon.reading_counter.databinding.ActivityMainBinding;
import com.leon.reading_counter.utils.CustomToast;
import com.leon.reading_counter.utils.GPSTracker;

import java.util.ArrayList;
import java.util.Objects;

import static com.leon.reading_counter.MyApplication.GPS_CODE;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        initialize();
        initializeMap();
    }

    void initialize() {
    }

    void initializeMap() {
        if (gpsEnabled())
            if (accessLocation()) {
                GPSTracker gpsTracker = new GPSTracker(this);
                Log.e("location getAccuracy", String.valueOf(gpsTracker.getLocation().getAccuracy()));
            }
    }

    void forceClose() {
        CustomToast customToast = new CustomToast();
        customToast.error(getString(R.string.permission_not_completed));
        finishAffinity();
    }

    void askLocationPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                CustomToast customToast = new CustomToast();
                customToast.info(context.getString(R.string.access_granted));
                initializeMap();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                forceClose();
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

    boolean accessLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            askLocationPermission();
            return false;
        }
        return true;
    }

    boolean gpsEnabled() {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled =
                LocationManagerCompat.isLocationEnabled(Objects.requireNonNull(locationManager));
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if (!enabled) {
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getApplicationContext().getString(R.string.gps_setting));
            alertDialog.setMessage(R.string.active_gps);
            alertDialog.setPositiveButton(R.string.setting, (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, GPS_CODE);
            });
            alertDialog.setNegativeButton(R.string.close, (dialog, which) -> {
                dialog.cancel();
                forceClose();
            });
            alertDialog.show();
        }
        return enabled;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == GPS_CODE)
                initializeMap();
        }
    }
}