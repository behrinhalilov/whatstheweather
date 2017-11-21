package com.bulpros.whatstheweather.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bulpros.whatstheweather.R;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class SplashActivity extends AppCompatActivity{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },2000);

    }

    public void startMainActivity() {
        Intent intent = new Intent(this,WeatherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
        finish();
    }
}
