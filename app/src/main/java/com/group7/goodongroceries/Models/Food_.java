
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
    "sr",
    "type",
    "desc",
    "nutrients",
    "footnotes"
})
public class Food_ {

    @JsonProperty("sr")
    private String sr;
    @JsonProperty("type")
    private String type;
    @JsonProperty("desc")
    private Desc desc;
    @JsonProperty("nutrients")
    private List<Nutrient> nutrients = null;
    @JsonProperty("footnotes")
    private List<Object> footnotes = null;
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

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("desc")
    public Desc getDesc() {
        return desc;
    }

    @JsonProperty("desc")
    public void setDesc(Desc desc) {
        this.desc = desc;
    }

    @JsonProperty("nutrients")
    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    @JsonProperty("nutrients")
    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    @JsonProperty("footnotes")
    public List<Object> getFootnotes() {
        return footnotes;
    }

    @JsonProperty("footnotes")
    public void setFootnotes(List<Object> footnotes) {
        this.footnotes = footnotes;
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
