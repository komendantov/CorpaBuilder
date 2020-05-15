package ru.komendantov.corpabuilder.models.corpus;

import lombok.Data;

import java.util.List;

@Data
public class Corpus {
    private String authorLogin;
    private String title;
    private List<CorpusWord> wordList;
}
