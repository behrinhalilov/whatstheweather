package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.helpers.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class BaseInteractor {

    public Map<String,String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("APPID", Constants.APPID);
        params.put("units", "metric");
        params.put("lang", "en");
        return params;
    }
}
