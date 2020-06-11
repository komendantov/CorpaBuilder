package ru.komendantov.corpabuilder.models.corpus;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "corps")
public class Corpus {
    private String authorLogin;
    private String title;
    private List<CorpusWord> wordList;
}
