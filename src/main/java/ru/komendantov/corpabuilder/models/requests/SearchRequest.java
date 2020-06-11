package ru.komendantov.corpabuilder.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String lex;
    private String text;
    private String gr;
    private String username;
}