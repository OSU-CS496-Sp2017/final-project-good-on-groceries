
package com.group7.goodongroceries.Models.NutritionResult;

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
    "sr",
    "groups",
    "subset",
    "end",
    "start",
    "total",
    "foods"
})
public class Report {

    @JsonProperty("sr")
    private String sr;
    @JsonProperty("groups")
    private String groups;
    @JsonProperty("subset")
    private String subset;
    @JsonProperty("end")
    private Integer end;
    @JsonProperty("start")
    private Integer start;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("foods")
    private List<Food> foods = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sr")
    public String getSr() {
        return sr;
    }

    @JsonProperty("sr")
    public void setSr(String sr) {
        this.sr = sr;
    }

    @JsonProperty("groups")
    public String getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(String groups) {
        this.groups = groups;
    }

    @JsonProperty("subset")
    public String getSubset() {
        return subset;
    }

    @JsonProperty("subset")
    public void setSubset(String subset) {
        this.subset = subset;
    }

    @JsonProperty("end")
    public Integer getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(Integer end) {
        this.end = end;
    }

    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("foods")
    public List<Food> getFoods() {
        return foods;
    }

    @JsonProperty("foods")
    public void setFoods(List<Food> foods) {
        this.foods = foods;
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
