package com.group7.goodongroceries.Utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class USDAUtils {

    private final static String USDA_FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private final static String USDA_FORECAST_QUERY_PARAM = "q";
    private final static String USDA_FORECAST_UNITS_PARAM = "units";
    private final static String USDA_FORECAST_APPID_PARAM = "appid";
    private final static String USDA_FORECAST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String USDA_FORECAST_TIME_ZONE = "UTC";

    private final static String USDA_API_KEY = "4074a8f5-dc8a-4b5b-bf38-fc5d8f500667";

    public static class FoodItem implements Serializable {

    }

    public static String buildForecastURL(String forecastLocation, String temperatureUnits) {
        return Uri.parse(USDA_FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(USDA_FORECAST_QUERY_PARAM, forecastLocation)
                .appendQueryParameter(USDA_FORECAST_UNITS_PARAM, temperatureUnits)
                .appendQueryParameter(USDA_FORECAST_APPID_PARAM, USDA_API_KEY)
                .build()
                .toString();
    }

    public static Exception parseForecastJSON(String forecastJSON) {
        return new UnsupportedOperationException();
    }
}
