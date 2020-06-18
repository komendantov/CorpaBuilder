package ru.komendantov.corpabuilder.models.document;

import com.fasterxml.jackson.annotation.*;
import ru.komendantov.corpabuilder.models.Analysis;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "analysis",
        "text"
})
public class DocumentWord implements Serializable {

    @JsonProperty("analysis")
    private Analysis analysis = null;
    @JsonProperty("text")
    private String text;

    /**
     * No args constructor for use in serialization
     */
    public DocumentWord() {
    }

    /**
     * @param text
     * @param analysis
     */
    public DocumentWord(Analysis analysis, String text) {
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

    public DocumentWord withAnalysis(Analysis analysis) {
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

    public DocumentWord withText(String text) {
        this.text = text;
        return this;
    }


}