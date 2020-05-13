package ru.komendantov.corpabuilder.models;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lex",
        "gr"
})
public class Analysis implements Serializable {

    private final static long serialVersionUID = -7392792371225545626L;
    @JsonProperty("lex")
    private String lex;
    @JsonProperty("gr")
    private String gr;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
