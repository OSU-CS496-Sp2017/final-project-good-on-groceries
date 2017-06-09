
package com.group7.goodongroceries.Models.NutritionResult;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nutrient_id",
    "nutrient",
    "unit",
    "value",
    "gm"
})
public class Nutrient {

    @JsonProperty("nutrient_id")
    private String nutrientId;
    @JsonProperty("nutrient")
    private String nutrient;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("value")
    private String value;
    @JsonProperty("gm")
    private Double gm;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nutrient_id")
    public String getNutrientId() {
        return nutrientId;
    }

    @JsonProperty("nutrient_id")
    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

    @JsonProperty("nutrient")
    public String getNutrient() {
        return nutrient;
    }

    @JsonProperty("nutrient")
    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("gm")
    public Double getGm() {
        return gm;
    }

    @JsonProperty("gm")
    public void setGm(Double gm) {
        this.gm = gm;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
