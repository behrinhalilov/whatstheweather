package com.demo.whatstheweather;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import com.demo.whatstheweather.database.AppDatabase;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class ApplicationClass extends Application {

    private static ApplicationClass instance;
    private AppDatabase appDatabase;
    private static SharedPreferences mPrefs;

    public ApplicationClass() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDatabase();
        initPreferences();
    }

    private void initPreferences() {
        mPrefs = instance.getSharedPreferences("weather_prefs",MODE_PRIVATE);
    }

    public static SharedPreferences getPrefs() {
        return mPrefs;
    }

    private void initDatabase() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "weather_db")
                .fallbackToDestructiveMigration()
                .build();
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
