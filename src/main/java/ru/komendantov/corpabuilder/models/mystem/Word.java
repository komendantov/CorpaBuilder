package ru.komendantov.corpabuilder.models.mystem;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "analysis",
        "text"
})
public class Word implements Serializable {

    private final static long serialVersionUID = 4792518248327123081L;
    @JsonProperty("analysis")
    private List<Analysis> analysis = null;
    @JsonProperty("text")
    private String text;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Word() {
    }

    /**
     * @param text
     * @param analysis
     */
    public Word(List<Analysis> analysis, String text) {
        super();
        this.analysis = analysis;
        this.text = text;
    }

    @JsonProperty("analysis")
    public List<Analysis> getAnalysis() {
        return analysis;
    }

    @JsonProperty("analysis")
    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    }

    public Word withAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
        return this;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    public Word withText(String text) {
        this.text = text;
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

    public Word withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
