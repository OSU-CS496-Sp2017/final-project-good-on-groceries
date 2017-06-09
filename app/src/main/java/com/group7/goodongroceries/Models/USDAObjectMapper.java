package com.group7.goodongroceries.Models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group7.goodongroceries.Models.NutritionResult.USDANutritionResult;
import com.group7.goodongroceries.Models.SearchResult.USDASearchResult;

import java.io.IOException;

/**
 * Created by codym on 6/6/2017.
 */
// Class to transform json strings into our objects
public class USDAObjectMapper
{
    public static USDANutritionResult NutritionResultConverter(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        USDANutritionResult result = mapper.readValue(json, USDANutritionResult.class);
        return result;
    }

    public static USDASearchResult SearchResultConverter(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        USDASearchResult result = mapper.readValue(json, USDASearchResult.class);
        return result;
    }

}
