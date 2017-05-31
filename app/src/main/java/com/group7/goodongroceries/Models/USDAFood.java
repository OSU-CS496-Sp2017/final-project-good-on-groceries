
package com.group7.goodongroceries.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "foods",
    "count",
    "notfound",
    "api"
})
public class USDAFood {

    @JsonProperty("foods")
    private List<Food> foods = null;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("notfound")
    private Integer notfound;
    @JsonProperty("api")
    private Double api;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("foods")
    public List<Food> getFoods() {
        return foods;
    }

    @JsonProperty("foods")
    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("notfound")
    public Integer getNotfound() {
        return notfound;
    }

    @JsonProperty("notfound")
    public void setNotfound(Integer notfound) {
        this.notfound = notfound;
    }

    @JsonProperty("api")
    public Double getApi() {
        return api;
    }

    @JsonProperty("api")
    public void setApi(Double api) {
        this.api = api;
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
