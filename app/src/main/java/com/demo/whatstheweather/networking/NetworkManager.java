package com.demo.whatstheweather.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.demo.whatstheweather.ApplicationClass;
import com.demo.whatstheweather.helpers.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class NetworkManager {

    private static Retrofit retrofit = null;

    public static Retrofit instance() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static boolean hasNetworkAccess() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ApplicationClass.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
