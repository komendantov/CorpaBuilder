package ru.komendantov.corpabuilder.models.requests;

import lombok.Data;
import lombok.NonNull;

@Data
public class AnalysePostRequest {
    @NonNull
    private String text;
}