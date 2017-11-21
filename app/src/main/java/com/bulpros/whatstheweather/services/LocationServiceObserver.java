package com.bulpros.whatstheweather.services;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.widget.Toast;
import com.bulpros.whatstheweather.ApplicationClass;
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

    /*

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void startLocationService() {

        Toast.makeText(activity,"Location service started.",Toast.LENGTH_SHORT).show();

        PeriodicTask mLocationTask = new PeriodicTask.Builder()
                .setService(LocationService.class)
                .setTag(LocationService.TASK_GET_LOCATION_PERIODIC)
                .setPeriod(30l)
                .setPersisted(true)
                .build();

        GcmNetworkManager
                .getInstance(ApplicationClass.getInstance().getApplicationContext())
                .schedule(mLocationTask);
    }

    */

}
