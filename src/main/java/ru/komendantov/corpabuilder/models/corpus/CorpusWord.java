package ru.komendantov.corpabuilder.models.corpus;

import com.fasterxml.jackson.annotation.*;
import ru.komendantov.corpabuilder.models.Analysis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "analysis",
        "text"
})
public class CorpusWord implements Serializable {

    @JsonProperty("analysis")
    private Analysis analysis = null;
    @JsonProperty("text")
    private String text;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public CorpusWord() {
    }

    /**
     * @param text
     * @param analysis
     */
    public CorpusWord(Analysis analysis, String text) {
        super();
        this.analysis = analysis;
        this.text = text;
    }

    @JsonProperty("analysis")
    public Analysis getAnalysis() {
        return analysis;
    }

    @JsonProperty("analysis")
    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public CorpusWord withAnalysis(Analysis analysis) {
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

    public CorpusWord withText(String text) {
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

    public CorpusWord withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
