
package com.group7.goodongroceries.Models;

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
    "label",
    "eqv",
    "eunit",
    "qty",
    "value"
})
public class Measure {

    @JsonProperty("label")
    private String label;
    @JsonProperty("eqv")
    private Double eqv;
    @JsonProperty("eunit")
    private String eunit;
    @JsonProperty("qty")
    private Double qty;
    @JsonProperty("value")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("eqv")
    public Double getEqv() {
        return eqv;
    }

    @JsonProperty("eqv")
    public void setEqv(Double eqv) {
        this.eqv = eqv;
    }

    @JsonProperty("eunit")
    public String getEunit() {
        return eunit;
    }

    @JsonProperty("eunit")
    public void setEunit(String eunit) {
        this.eunit = eunit;
    }

    @JsonProperty("qty")
    public Double getQty() {
        return qty;
    }

    @JsonProperty("qty")
    public void setQty(Double qty) {
        this.qty = qty;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
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
