
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
    "ndbno",
    "name",
    "weight",
    "measure",
    "nutrients"
})
public class Food {

    @JsonProperty("ndbno")
    private String ndbno;
    @JsonProperty("name")
    private String name;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("measure")
    private String measure;
    @JsonProperty("nutrients")
    private List<Nutrient> nutrients = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ndbno")
    public String getNdbno() {
        return ndbno;
    }

    @JsonProperty("ndbno")
    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("weight")
    public Double getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @JsonProperty("measure")
    public String getMeasure() {
        return measure;
    }

    @JsonProperty("measure")
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @JsonProperty("nutrients")
    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    @JsonProperty("nutrients")
    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
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
