package ru.komendantov.corpabuilder.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

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

    @JsonProperty("qual")
    private String qual;

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


    @JsonProperty("gr")
    public String getGr() {
        return gr;
    }

    @JsonProperty("gr")
    public void setGr(String gr) {
        this.gr = gr;
    }


    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }


}
