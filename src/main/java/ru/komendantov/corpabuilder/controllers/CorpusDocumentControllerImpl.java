package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public List<SearchResult> search(@RequestBody SearchRequest searchRequest, Integer page) {


        //documentRepository.getAllByTitle()


        List<CorpusDocument> s = documentRepository.getAllByAuthorUsername(searchRequest.getUsername());

        s.toString();


//        List<CorpusDocument> s1 = documentRepository.getAllByAuthorUsernameAndAndWords(searchRequest.getUsername(), searchRequest.getGr());
      //  s1.toString();
        //        Page<TextRepository> pag = textRepository.findAll();
        SearchResult hh = new SearchResult();
        //hh.setCorpusDocumentID("tyhft5564345");


        ArrayList<DocumentWord> hh1 = new ArrayList<>();

        hh.setDocumentExcerpt(hh1);
        List<SearchResult> results = new ArrayList<>();
        return results;
    }

    @Override
    @GetMapping("/{id}")
    public CorpusDocument getCorpusDocument(@PathVariable String id) {
        return documentRepository.getFirstBy_id(id);
    }

    @Override
    @GetMapping("/user/me")
    public ArrayList<SearchResult> getUserDocuments() {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> changeCorpus(String documentId) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCorpus(String documentId) {
        return null;
    }

//    public ResponseEntity<String>

}
