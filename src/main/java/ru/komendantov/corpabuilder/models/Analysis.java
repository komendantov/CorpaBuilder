
package ru.komendantov.corpabuilder.models;

import java.io.Serializable;
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
        "lex",
        "gr"
})
public class Analysis implements Serializable {

    @JsonProperty("lex")
    private String lex;
    @JsonProperty("gr")
    private String gr;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -7392792371225545626L;

    /**
     * No args constructor for use in serialization
     */
    public Analysis() {
    }

    /**
     * @param gr
     * @param lex
     */
    public Analysis(String lex, String gr) {
        super();
        this.lex = lex;
        this.gr = gr;
    }

    @JsonProperty("lex")
    public String getLex() {
        return lex;
    }

    @JsonProperty("lex")
    public void setLex(String lex) {
        this.lex = lex;
    }

    public Analysis withLex(String lex) {
        this.lex = lex;
        return this;
    }

    @JsonProperty("gr")
    public String getGr() {
        return gr;
    }

    @JsonProperty("gr")
    public void setGr(String gr) {
        this.gr = gr;
    }

    public Analysis withGr(String gr) {
        this.gr = gr;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Analysis withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
