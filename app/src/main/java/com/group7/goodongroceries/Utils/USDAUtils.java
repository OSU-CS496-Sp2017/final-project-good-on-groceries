package com.group7.goodongroceries.Utils;

import android.net.Uri;

import com.group7.goodongroceries.Models.Nutrient;

import java.io.Serializable;

public class USDAUtils {

    private final static String USDA_SEARCH_BASE_URL = "https://api.nal.usda.gov/ndb/search/";
    private final static String USDA_NUTRIENT_BASE_URL = "https://api.nal.usda.gov/ndb/nutrients/";

    private final static String USDA_QUERY_PARAM = "q";

    private final static String USDA_FORMAT_PARAM = "format";
    private final static String USDA_FORMAT_VAL = "json";

    private final static String USDA_SORT_PARAM = "sort";
    private final static String USDA_SORT_VAL = "n"; // sort according to name

    private final static String USDA_MAX_PARAM = "total";
    private final static String USDA_MAX_VAL = "20"; // Total items returned

    private final static String USDA_API_KEY_PARAM = "api_key";
    public final static String USDA_API_KEY = "AiC0tHOnH56DABVtkqKpXkHD6T4Px54virs0mvW2";

    private final static String USDA_NUTRIENTS_PARAM = "nutrients";
    private final static String USDA_NDBNO_PARAM = "ndbno";

    private static String sugar_ndbo = "269";
    private static String carbs_ndbo = "205";
    private static String calories_ndbo = "208";
    private static String lipidfat_ndbo = "204";

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

    // Search for specific food using ndbno
    public static String buildNutrientQueryURL(String ndbno) {
        return Uri.parse(USDA_NUTRIENT_BASE_URL).buildUpon()
                .appendQueryParameter(USDA_FORMAT_PARAM, USDA_FORMAT_VAL)
                .appendQueryParameter(USDA_NDBNO_PARAM, ndbno)
                .appendQueryParameter(USDA_NUTRIENTS_PARAM, sugar_ndbo)
                .appendQueryParameter(USDA_NUTRIENTS_PARAM, calories_ndbo)
                .appendQueryParameter(USDA_NUTRIENTS_PARAM, carbs_ndbo)
                .appendQueryParameter(USDA_NUTRIENTS_PARAM, lipidfat_ndbo)
                .appendQueryParameter(USDA_API_KEY_PARAM, USDA_API_KEY)
                .build()
                .toString();
    }

    public static Exception parseForecastJSON(String forecastJSON) {
        return new UnsupportedOperationException();
    }
}
