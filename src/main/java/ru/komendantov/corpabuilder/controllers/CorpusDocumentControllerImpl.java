package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;
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

@RestController
@RequestMapping("/api/v1/document")
@CrossOrigin(origins = "", allowedHeaders = "*", maxAge = 3600)
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

//    @Autowired
//    CorpusDocumentRepository documentRepository;

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

    @PostMapping("/save")
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

        requestResult.stream().forEach(document -> {


            SearchResult searchResult = new SearchResult();
            searchResult.setAuthorUsername(document.getAuthorUsername());
            searchResult.setDocumentID(document.get_id());
            searchResult.setDocumentTitle(document.getTitle());

            List<DocumentWord> documentExcept;
            if (document.getWords().size() > 10)
                documentExcept = document.getWords().subList(0, 10);
            else
                documentExcept = document.getWords().subList(0, document.getWords().size());

//            int startIndex = 0;
//            for (int i = 0; i < requestResult.size(); i++
//            ) {
//                 startIndex = 0;
//                if (searchRequest.getTitle() != null && !searchRequest.getTitle().isEmpty() && requestResult.get(i).getTitle().contains(searchRequest.getTitle())) {
//                    startIndex = 1;
//                }
//                if (searchRequest.getText() != null && !searchRequest.getText().isEmpty()) {
//
//                    for (int j = 0; j < requestResult.get(i).getWords().size(); j++) {
//                        if (requestResult.get(i).getWords().get(j).getText().contains(searchRequest.getText())) {
//                            startIndex = j;
//                        }
//                        if (requestResult.get(i).getWords().get(j).getAnalysis().getGr().contains(searchRequest.getGr())) {
//                            startIndex = j;
//                        }
//                        if (requestResult.get(i).getWords().get(j).getAnalysis().getLex().contains(searchRequest.getLex())) {
//                            startIndex = j;
//                        }
//                    }
//                }


//            }
//            for (int s = startIndex; s < startIndex + 6 && s < requestResult.get(i).getWords().size(); s++) {
//                DocumentWord d = requestResult.get(i).getWords().get(s);
//                if (s == startIndex) {
//                    String te = "<b>" + d.getText() + "</b>";
//                    d.setText(te);
//                }
//                documentExcept.add(d);
//            }


            searchResult.setDocumentExcerpt(documentExcept);
            results.add(searchResult);

        });


        return results;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity getCorpusDocument(@PathVariable String id) {
        Optional<CorpusDocument> corpusDocument = documentRepository.getFirstBy_id(id);
        return corpusDocument.map(document -> new ResponseEntity(document, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
