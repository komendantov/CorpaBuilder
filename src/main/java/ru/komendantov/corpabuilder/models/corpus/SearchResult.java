package ru.komendantov.corpabuilder.models.corpus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private String corpusID;
    private ArrayList<CorpusWord> coupusEcerpt;
}