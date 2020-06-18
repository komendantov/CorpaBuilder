package ru.komendantov.corpabuilder.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.komendantov.corpabuilder.models.document.CorpusDocument;

import java.util.List;

public interface CorpusDocumentRepository extends MongoRepository<CorpusDocument, String> {

     List<CorpusDocument> getAllByTitle(String title);

     List<CorpusDocument> getAllByWordsAnalysisLex(String s);


     List<CorpusDocument> getAllByAuthorUsername(String username);

//     List<CorpusDocument> getAllByAuthorUsernameAndAndWordsA_AnalysisA_Gr(String username,String s);
@Query("{ 'items': { $elemMatch: { 'refund.id' :  ?0 } } }")
List<CorpusDocument> findRMAByItemRefund(String refundId);

    ///   public List<Word> getAllByWords
    // public Word findByText(String firstName);
    //  public List<Word> findByLastName(String lastName);
//    public List<Text> findByText(String text);
}