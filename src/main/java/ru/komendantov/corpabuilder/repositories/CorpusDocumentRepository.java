package ru.komendantov.corpabuilder.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.komendantov.corpabuilder.models.document.CorpusDocument;


import java.util.List;

public interface CorpusDocumentRepository extends MongoRepository<CorpusDocument, String> {

    public List<CorpusDocument> getAllByTitle(String title);

    public List<CorpusDocument> getAllByWordsAnalysisLex(String s);

    ///   public List<Word> getAllByWords
    // public Word findByText(String firstName);
    //  public List<Word> findByLastName(String lastName);
//    public List<Text> findByText(String text);
}