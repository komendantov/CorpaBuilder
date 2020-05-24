package ru.komendantov.corpabuilder.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnalysePostRequest {
    @NotBlank
    private String text;
}