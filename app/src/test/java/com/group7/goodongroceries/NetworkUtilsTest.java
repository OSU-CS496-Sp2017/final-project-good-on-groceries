package com.group7.goodongroceries;


import android.util.Log;

import com.group7.goodongroceries.Utils.NetworkUtils;
import com.group7.goodongroceries.Utils.USDAUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import java.io.Console;

import static org.junit.Assert.*;

/**
 * Created by codym on 6/2/2017.
 */

// Roboelectric makes testing simple uri builders easy
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NetworkUtilsTest {
    @Test
    public void buidSearchQueryURL_isCorrect() throws Exception {
        String searchTerm = "butter";
        String expected = "https://api.nal.usda.gov/ndb/search/?format=json&q=butter&sort=n&total=20&api_key=" + USDAUtils.USDA_API_KEY;
        String result = USDAUtils.buildSearchQueryURL(searchTerm);
        assertEquals(result, expected);
    }

    @Test
    public void buidNutrientQueryURL_isCorrect() throws Exception {
        String searchTerm = "01009";
        String expected = "https://api.nal.usda.gov/ndb/nutrients/?format=json&ndbno=01009&nutrients=269&nutrients=208&nutrients=205&nutrients=204&api_key=" + USDAUtils.USDA_API_KEY;
        String result = USDAUtils.buildNutrientQueryURL(searchTerm);
        assertEquals(result, expected);
    }

    @Test
    public void callUSDAAPITest() throws Exception {
        String searchTerm = "butter";
        String searchUrl = USDAUtils.buildSearchQueryURL(searchTerm);
        String result = NetworkUtils.doHTTPGet(searchUrl);
        Log.d("call usda api test", result);
        assertNotNull(result);
    }

}

