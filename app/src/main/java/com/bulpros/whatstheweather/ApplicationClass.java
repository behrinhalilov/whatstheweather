package com.bulpros.whatstheweather;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.bulpros.whatstheweather.database.AppDatabase;
import com.bulpros.whatstheweather.networking.NetworkManager;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class ApplicationClass extends Application {

    private static ApplicationClass instance;
    private AppDatabase appDatabase;

    public ApplicationClass() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDatabase();
    }

    private void initDatabase() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "weather_db").build();
    }

    public AppDatabase getCacheDb() {
        return appDatabase;
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
