package ru.komendantov.corpabuilder.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AnalysePostRequest {
    @NonNull
    private String text;
}