package ru.komendantov.corpabuilder.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.komendantov.corpabuilder.models.Text;

import java.util.List;

public interface TextRepository extends MongoRepository<Text, String> {

    public List<Text> getAllByTitle(String title);

    public List<Text> getAllByWordsAnalysisLex(String s);
    ///   public List<Word> getAllByWords
    // public Word findByText(String firstName);
    //  public List<Word> findByLastName(String lastName);
//    public List<Text> findByText(String text);
}