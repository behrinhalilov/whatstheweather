package com.demo.whatstheweather.services;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.demo.whatstheweather.ApplicationClass;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class LocationServiceObserver implements LifecycleObserver {

    private Activity activity;

    public LocationServiceObserver(Activity activity) {
        this.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void startLocationService() {

        PeriodicTask mLocationTask = new PeriodicTask.Builder()
                .setService(LocationService.class)
                .setTag(LocationService.TASK_GET_LOCATION_PERIODIC)
                .setPeriod(60L)
                .setPersisted(true)
                .build();

        GcmNetworkManager
                .getInstance(ApplicationClass.getInstance().getApplicationContext())
                .schedule(mLocationTask);

        Log.i(activity.getClass().getSimpleName(),"Starting location service...");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stopLocationService() {

        GcmNetworkManager
                .getInstance(ApplicationClass.getInstance().getApplicationContext())
                .cancelTask(LocationService.TASK_GET_LOCATION_PERIODIC, LocationService.class);
        Log.i(activity.getClass().getSimpleName(), "Stopping location service...");

    }
}
