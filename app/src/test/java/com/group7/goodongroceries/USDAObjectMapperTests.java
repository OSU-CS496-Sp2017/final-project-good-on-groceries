package com.group7.goodongroceries;


import com.group7.goodongroceries.Models.NutritionResult.USDANutritionResult;
import com.group7.goodongroceries.Models.SearchResult.List;
import com.group7.goodongroceries.Models.SearchResult.USDASearchResult;
import com.group7.goodongroceries.Models.USDAObjectMapper;
import com.group7.goodongroceries.Utils.NetworkUtils;
import com.group7.goodongroceries.Utils.USDAUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by codym on 6/2/2017.
 */

// Roboelectric makes testing simple uri builders easy
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class USDAObjectMapperTests {
    @Test
    public void USDANutritionResultMapperTest() throws Exception {
        String searchTerm = "butter";
        String searchURL = USDAUtils.buildSearchQueryURL(searchTerm);
        String searchJsonResult = NetworkUtils.doHTTPGet(searchURL);
        USDASearchResult objectResult = USDAObjectMapper.SearchResultConverter(searchJsonResult);

        String nutritionURL = USDAUtils.buildNutrientQueryURL(objectResult.getList().getItem().get(0).getNdbno());
        String nutritionJsonResult = NetworkUtils.doHTTPGet(nutritionURL);
        USDANutritionResult result = USDAObjectMapper.NutritionResultConverter(nutritionJsonResult);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getReport().getFoods().get(0).getName());
    }

    @Test
    public void USDASearchResultMapperTest() throws Exception {
        String searchTerm = "butter";
        String searchURL = USDAUtils.buildSearchQueryURL(searchTerm);
        String searchResult = NetworkUtils.doHTTPGet(searchURL);
        USDASearchResult objectResult = USDAObjectMapper.SearchResultConverter(searchResult);

        Assert.assertNotNull(objectResult);
        Assert.assertNotNull(objectResult.getList().getTotal());

        List listResults = objectResult.getList();
        Assert.assertNotSame(listResults.getQ(), "butter");
    }
}
