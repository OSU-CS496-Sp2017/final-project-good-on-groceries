
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
    "ndbno",
    "name",
    "ds",
    "ru"
})
public class Desc {

    @JsonProperty("ndbno")
    private String ndbno;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ds")
    private String ds;
    @JsonProperty("ru")
    private String ru;
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

    @JsonProperty("ds")
    public String getDs() {
        return ds;
    }

    @JsonProperty("ds")
    public void setDs(String ds) {
        this.ds = ds;
    }

    @JsonProperty("ru")
    public String getRu() {
        return ru;
    }

    @JsonProperty("ru")
    public void setRu(String ru) {
        this.ru = ru;
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
