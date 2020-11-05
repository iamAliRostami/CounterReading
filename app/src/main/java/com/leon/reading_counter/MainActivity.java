package com.leon.reading_counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.leon.reading_counter.databinding.ActivityMainBinding;
import com.leon.reading_counter.utils.CustomToast;

import org.osmdroid.config.Configuration;

import static com.leon.reading_counter.MyApplication.MIN_DISTANCE_CHANGE_FOR_UPDATES;
import static com.leon.reading_counter.MyApplication.MIN_TIME_BW_UPDATES;
import static com.leon.reading_counter.MyApplication.PLAY_SERVICES_RESOLUTION_REQUEST;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Context context;
    boolean canGetLocation = false, checkGPS = false, checkNetwork = false;
    Location location;
    LocationManager locationManager;
    LocationRequest locationRequest;
    GoogleApiClient googleApiClient;
    double latitude, longitude;
    android.location.LocationListener locationListener = new android.location.LocationListener() {
        public void onLocationChanged(Location location) {
            if (locationManager != null)
                locationManager.removeUpdates(locationListener);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e("accuracy2", String.valueOf(location.getAccuracy()));
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };
    LocationListener locationListenerGoogle = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e("accuracy1", String.valueOf(location.getAccuracy()));
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        initialize();
    }

    @SuppressLint("MissingPermission")
    void initialize() {
        if (checkGooglePlayServices()) {
            startFusedLocation();
        } else {
            getLocation();
        }
    }

    @SuppressLint("MissingPermission")
    void getLocation() {
        try {
            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);
            // get GPS status
            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // get network provider status
            checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!checkGPS && !checkNetwork) {
                CustomToast customToast = new CustomToast();
                customToast.warning(getString(R.string.services_is_not_available));
            } else {
                this.canGetLocation = true;
                if (checkNetwork) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);//TODO
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (checkGPS && location == null) {
                    if (locationManager != null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);//TODO
                    }
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
        new Handler().postDelayed(this::getLocation, MIN_TIME_BW_UPDATES);
    }

    boolean checkGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        CustomToast customToast = new CustomToast();
        String message;
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                message = context.getString(R.string.google_is_available_but_not_installed);
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                message = context.getString(R.string.google_is_not_available);
            }
            customToast.warning(message);
            return false;
        }
        return true;
    }

    void startFusedLocation() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(result -> {

                    }).build();
        }
        googleApiClient.connect();
        registerRequestUpdateGoogle(locationListenerGoogle);
    }

    void registerRequestUpdateGoogle(final LocationListener listener) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(MIN_TIME_BW_UPDATES);
        new Handler().postDelayed(() -> {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                        locationRequest, listener);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                if (!isGoogleApiClientConnected()) {
                    googleApiClient.connect();
                }
                registerRequestUpdateGoogle(listener);
            }
        }, MIN_TIME_BW_UPDATES);
    }

    boolean isGoogleApiClientConnected() {
        return googleApiClient != null && googleApiClient.isConnected();
    }

    void stopFusedLocation() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }
}