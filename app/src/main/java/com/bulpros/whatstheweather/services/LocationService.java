package com.bulpros.whatstheweather.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.AndroidException;
import android.util.Log;
import android.util.Pair;

import com.bulpros.whatstheweather.ApplicationClass;
import com.bulpros.whatstheweather.helpers.Constants;
import com.bulpros.whatstheweather.interfaces.OnLocationReceivedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import static android.content.ContentValues.TAG;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class LocationService extends GcmTaskService {

    public static final String TAG = LocationService.class.getSimpleName();
    private LocationManager locationManager;
    private Location mCurrentLocation;
    public static final String TASK_GET_LOCATION_ONCE = "location_oneoff_task";
    public static final String TASK_GET_LOCATION_PERIODIC = "location_periodic_task";

    @Override
    public void onInitializeTasks() {
        startPeriodicLocationTask(TASK_GET_LOCATION_PERIODIC, 30L, null);
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Log.d(TAG, "onRunTask: " + taskParams.getTag());

        String tag = taskParams.getTag();
        Bundle extras = taskParams.getExtras();
        // Default result is success.
        int result = GcmNetworkManager.RESULT_SUCCESS;

        switch (tag) {
            case TASK_GET_LOCATION_ONCE:
                getLastKnownLocation();
                break;

            case TASK_GET_LOCATION_PERIODIC:
                getLastKnownLocation();
                break;

        }

        return result;
    }


    public void getLastKnownLocation() {
        Location lastKnownGPSLocation;
        Location lastKnownNetworkLocation;
        String gpsLocationProvider = LocationManager.GPS_PROVIDER;
        String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

        try {
            locationManager = (LocationManager) ApplicationClass
                    .getInstance()
                    .getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE);

            lastKnownNetworkLocation = locationManager.getLastKnownLocation(networkLocationProvider);
            lastKnownGPSLocation = locationManager.getLastKnownLocation(gpsLocationProvider);

            if (lastKnownGPSLocation != null) {
                this.mCurrentLocation = lastKnownGPSLocation;
            } else if (lastKnownNetworkLocation != null) {
                this.mCurrentLocation = lastKnownNetworkLocation;
            } else {
                return;
            }


            Intent intent = new Intent(Constants.ACTION_LOCATION_UPDATE);
            intent.putExtra("lat", mCurrentLocation.getLatitude());
            intent.putExtra("lng", mCurrentLocation.getLongitude());
            ApplicationClass.getInstance().sendBroadcast(intent);

        } catch (SecurityException sex) {
            Log.e(TAG, "Location permission is not granted!");
        }

        return;
    }

    public static void startPeriodicLocationTask(String tag, Long period, Bundle extras) {

        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(ApplicationClass.getInstance());
        PeriodicTask.Builder taskBuilder = new PeriodicTask.Builder()
                .setService(LocationService.class)
                .setTag(tag)
                .setPeriod(period)
                .setPersisted(true)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED);

        if (extras != null) taskBuilder.setExtras(extras);

        PeriodicTask task = taskBuilder.build();
        mGcmNetworkManager.schedule(task);
    }

    public static boolean checkPlayServicesAvailable(Activity activity) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int resultCode = availability.isGooglePlayServicesAvailable(ApplicationClass.getInstance());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (availability.isUserResolvableError(resultCode)) {
                availability.getErrorDialog(activity, resultCode, Constants.RC_PLAY_SERVICES).show();
            }
            return false;
        } else {
            return true;
        }
    }
}
