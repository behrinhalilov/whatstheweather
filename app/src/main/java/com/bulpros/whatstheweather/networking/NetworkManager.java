package com.bulpros.whatstheweather.networking;

import com.bulpros.whatstheweather.helpers.Constants;

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

}
