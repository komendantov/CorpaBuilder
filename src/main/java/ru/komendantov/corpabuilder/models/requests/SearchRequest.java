package ru.komendantov.corpabuilder.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String lex;
    private String text;
    private String gr;
    private String authorUsername;
    private String title;
    private ArrayList<String> tags;
}