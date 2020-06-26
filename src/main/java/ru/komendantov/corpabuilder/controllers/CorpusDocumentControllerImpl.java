package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;
import ru.komendantov.corpabuilder.models.Analysis;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.models.document.CorpusDocument;
import ru.komendantov.corpabuilder.models.document.DocumentWord;
import ru.komendantov.corpabuilder.models.document.SearchResult;
import ru.komendantov.corpabuilder.models.requests.AnalysePostRequest;
import ru.komendantov.corpabuilder.models.requests.SearchRequest;
import ru.komendantov.corpabuilder.repositories.CorpusDocumentRepository;
import ru.komendantov.corpabuilder.services.CorpusDocumentUtils;
import ru.komendantov.corpabuilder.services.MystemService;
import ru.komendantov.corpabuilder.swagger.interfaces.CorpusDocumentController;
import ru.komendantov.corpabuilder.utils.UserUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/v1/document", produces = APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class CorpusDocumentControllerImpl implements CorpusDocumentController {
    @Autowired
    private MystemService mystemService;

    @Autowired
    private CorpusDocumentUtils documentUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    CorpusDocumentRepository documentRepository;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${application.searchExceptLength}")
    int exceptLength;

    @PostMapping("/analyse")
    public List<Word> analyseText(@RequestBody AnalysePostRequest analysePostRequest, @RequestParam(name = "doReplaces", defaultValue = "false")
            boolean doReplaces) throws IOException {
        String analysedText;
        String text = analysePostRequest.getText();
        HashMap<Integer, String> textWithReplacesMap;
        List<String> textList = new ArrayList<>();
        if (doReplaces) {
            String[] textArray = text.split("\\b");
            textList = Arrays.asList(textArray);
            textWithReplacesMap = documentUtils.doReplacesInText(userDetailsService.getUserReplaces(), textList);
            text = String.join(" ", textWithReplacesMap.values());
        }
        analysedText = mystemService.analyseText(text);
        if (doReplaces) {
            analysedText = documentUtils.revertReplaces(analysedText, textList).toString();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(analysedText, new TypeReference<List<Word>>() {
        });
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCorpusDocument(@RequestBody CorpusDocument document) {
        document.setAuthorID(userUtils.getUser().getId());
        document.setAuthorUsername(userUtils.getUser().getUsername());
        documentRepository.insert(document);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }


    @PostMapping("/search")
    public List<SearchResult> search(@RequestBody SearchRequest searchRequest) {

        Query query = new Query();
        if (searchRequest.getTitle() != null && !searchRequest.getTitle().isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(searchRequest.getTitle()));
        }
        if (searchRequest.getText() != null && !searchRequest.getText().isEmpty()) {
            query.addCriteria(Criteria.where("words.text").regex(searchRequest.getText()));
        }
        if (searchRequest.getGr() != null && !searchRequest.getGr().isEmpty()) {
            query.addCriteria(Criteria.where("words.analysis.gr").regex(searchRequest.getGr()));
        }
        if (searchRequest.getAuthorUsername() != null && !searchRequest.getAuthorUsername().isEmpty()) {
            query.addCriteria(Criteria.where("authorUsername").regex(searchRequest.getAuthorUsername()));
        }
        if (searchRequest.getLex() != null && !searchRequest.getLex().isEmpty()) {
            query.addCriteria(Criteria.where("words.analysis.lex").regex(searchRequest.getLex()));
        }

        if (searchRequest.getTags() != null && !searchRequest.getTags().isEmpty()) {
            query.addCriteria(Criteria.where("tags").in(searchRequest.getTags()));
        }

        List<CorpusDocument> requestResult = mongoTemplate.find(query, CorpusDocument.class);

        List<SearchResult> results = new ArrayList<>();

        requestResult.forEach(document -> {
            List<DocumentWord> documentWords = document.getWords();
            SearchResult searchResult = new SearchResult();
            searchResult.setAuthorUsername(document.getAuthorUsername());
            searchResult.setDocumentID(document.get_id());
            searchResult.setDocumentTitle(document.getTitle());

            ArrayList<Integer> exceptIndex = new ArrayList<>();
            for (int i = 0; i < documentWords.size(); i++) {
//                exceptIndex = 0;
                String text = documentWords.get(i).getText();
                Analysis analysis = documentWords.get(i).getAnalysis();

                if (text != null && !text.isEmpty() && searchRequest.getText() != null && !searchRequest.getText().isEmpty() && text.contains(searchRequest.getText())) {
                    //     text = "<b>" + text + "</b>";
                    documentWords.get(i).setText(text);
                    exceptIndex.add(i);

                }


                if (analysis != null && !analysis.getLex().isEmpty() && searchRequest.getLex() != null && !searchRequest.getLex().isEmpty() && analysis.getLex().contains(searchRequest.getLex())) {
                    //    text = "<b>" + text + "</b>";
                    //   documentWords.get(i).setText(text);
                    exceptIndex.add(i);

                }

                if (analysis != null && !analysis.getGr().isEmpty() && searchRequest.getGr() != null && !searchRequest.getGr().isEmpty() && analysis.getGr().contains(searchRequest.getGr())) {
                    //    text = "<b>" + text + "</b>";
                    //     documentWords.get(i).setText(text);
                    exceptIndex.add(i);
                }
            }

            List<DocumentWord> documentExcept;

            for (Integer index : exceptIndex) {
                if (document.getWords().size() > index + exceptLength)
                    documentExcept = documentWords.subList(index, index + exceptLength);
                else
                    documentExcept = documentWords.subList(index, document.getWords().size());
                searchResult.setDocumentExcerpt(documentExcept);
                results.add(searchResult);
            }
        });


        return results;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CorpusDocument> getCorpusDocument(@PathVariable String id) {
        Optional<CorpusDocument> corpusDocument = documentRepository.getFirstBy_id(id);
        return corpusDocument.map(document -> new ResponseEntity<>(document, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @GetMapping("/user/me")
    public List<CorpusDocument> getUserDocuments() {
        return documentRepository.getAllByAuthorUsername(userUtils.getUser().getUsername());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> changeCorpus(@PathVariable String id, @RequestBody CorpusDocument document) {
        Optional<CorpusDocument> corpusDocument = documentRepository.getFirstBy_id(id);
        if (corpusDocument.isPresent()) {
            CorpusDocument documentFromDb = corpusDocument.get();
            documentFromDb.setDateOfChange(LocalDateTime.now());
            documentFromDb.setTags(document.getTags());
            documentFromDb.setTitle(document.getTitle());
            documentFromDb.setWords(document.getWords());
            documentRepository.save(documentFromDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCorpus(@PathVariable String id) {
        try {
            documentRepository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    public ResponseEntity<String>

}
