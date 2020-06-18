package ru.komendantov.corpabuilder.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.komendantov.corpabuilder.models.document.CorpusDocument;

import java.util.List;
import java.util.Optional;

public interface CorpusDocumentRepository extends MongoRepository<CorpusDocument, String> {

    List<CorpusDocument> getAllByTitle(String title);

    List<CorpusDocument> getAllByWordsAnalysisLex(String s);

    List<CorpusDocument> getAllByAuthorUsername(String username);

    Optional<CorpusDocument> getFirstByAuthorUsername(String authorUsername);

    Optional<CorpusDocument> getFirstBy_id(String _id);



//    Optional<CorpusDocument>

}