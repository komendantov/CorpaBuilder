package ru.komendantov.corpabuilder.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.komendantov.corpabuilder.models.document.CorpusDocument;


import java.util.List;

public interface CorpusDocumentRepository extends MongoRepository<CorpusDocument, String> {

    public List<CorpusDocument> getAllByTitle(String title);

     List<CorpusDocument> getAllByWordsAnalysisLex(String s);
//     CorpusDocument getAllById(String id);

     List<CorpusDocument> getAllByAuthorUsername(String username);

//     @Query("{_id: { $in: ?0 } })")
//     List<CorpusDocument> findByIds(List<String> id);
//
////     List<CorpusDocument> getAllByAuthorUsernameAndAndWordsA_AnalysisA_Gr(String username,String s);
//@Query("{ 'items': { $elemMatch: { 'refund.id' :  ?0 } } }")
//List<CorpusDocument> findRMAByItemRefund(String refundId);

CorpusDocument getFirstByAuthorUsername(String authorUsername);

CorpusDocument getFirstBy_id(String _id);
    ///   public List<Word> getAllByWords
    // public Word findByText(String firstName);
    //  public List<Word> findByLastName(String lastName);
//    public List<Text> findByText(String text);
}