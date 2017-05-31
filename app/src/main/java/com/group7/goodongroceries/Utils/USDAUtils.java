package com.group7.goodongroceries.Utils;

import android.net.Uri;

import java.io.Serializable;

public class USDAUtils {

    private final static String USDA_REPORT_BASE_URL = "https://api.nal.usda.gov/ndb/V2/reports";
    private final static String USDA_SEARCH_BASE_URL = "https://api.nal.usda.gov/ndb/search/";

    private final static String USDA_QUERY_PARAM = "q";

    private final static String USDA_FORMAT_PARAM = "format";
    private final static String USDA_FORMAT_VAL = "json";

    private final static String USDA_SORT_PARAM = "sort";
    private final static String USDA_SORT_VAL = "n"; // sort according to name

    private final static String USDA_MAX_PARAM = "total";
    private final static String USDA_MAX_VAL = "20"; // Total items returned

    private final static String USDA_API_KEY_PARAM = "api_key";
    private final static String USDA_API_KEY = "4074a8f5-dc8a-4b5b-bf38-fc5d8f500667";

    public static class FoodItem implements Serializable {

    }

    // Example: https://api.nal.usda.gov/ndb/search/?format=json&q=butter&sort=n&max=25&offset=0&api_key=DEMO_KEY
    // https://ndb.nal.usda.gov/ndb/doc/apilist/API-SEARCH.md
    public static String buildSearchQueryURL(String food) {
        return Uri.parse(USDA_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(USDA_FORMAT_PARAM, USDA_FORMAT_VAL)
                .appendQueryParameter(USDA_QUERY_PARAM, food)
                .appendQueryParameter(USDA_SORT_PARAM, USDA_SORT_VAL)
                .appendQueryParameter(USDA_MAX_PARAM, USDA_MAX_VAL)
                .appendQueryParameter(USDA_API_KEY_PARAM, USDA_API_KEY)
                .build()
                .toString();
    }

    public static Exception parseForecastJSON(String forecastJSON) {
        return new UnsupportedOperationException();
    }
}
