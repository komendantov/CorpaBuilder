package ru.komendantov.corpabuilder.models.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private String documentID;
    private String authorUsername;
    private String documentTitle;
    private List<DocumentWord> documentExcerpt;
    private Integer markedWordIndex = 0;
}