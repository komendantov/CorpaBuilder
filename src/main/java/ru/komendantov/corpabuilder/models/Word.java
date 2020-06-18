package ru.komendantov.corpabuilder.models;

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

    @JsonProperty("analysis")
    private List<Analysis> analysis = null;
    @JsonProperty("text")
    private String text;

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


    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }


}
