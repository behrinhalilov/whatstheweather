package com.bulpros.whatstheweather;

import android.app.Application;

import com.bulpros.whatstheweather.networking.NetworkManager;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class ApplicationClass extends Application {

    private static ApplicationClass instance;

    public ApplicationClass() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static ApplicationClass getInstance() {
        if (instance == null) {
            synchronized (ApplicationClass.class) {
                if (instance == null) {
                    instance = new ApplicationClass();
                }
            }
        }
        return instance;
    }
}
