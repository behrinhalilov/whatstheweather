package com.demo.whatstheweather.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.demo.whatstheweather.ApplicationClass;
import com.demo.whatstheweather.R;
import com.demo.whatstheweather.helpers.Constants;
import com.demo.whatstheweather.services.LocationService;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LocationService.checkPlayServicesAvailable(this);
        requestPermissions();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
        finish();
    }

    private void requestPermissions() {
        if (ContextCompat
                .checkSelfPermission(ApplicationClass.getInstance(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            Constants.REQUEST_PERMISSIONS_LOCATION);
        } else {
            goToMain();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_PERMISSIONS_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   goToMain();
                } else {
                    finish();
                }
            }

        }
    }

    private void goToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 2000);
    }

}
