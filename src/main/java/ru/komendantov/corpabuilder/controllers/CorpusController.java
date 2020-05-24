package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.models.requests.AnalysePostRequest;
import ru.komendantov.corpabuilder.services.CorpusUtils;
import ru.komendantov.corpabuilder.services.MystemService;

import java.io.IOException;
import java.util.*;

@RestController("/api/v1/corpus")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CorpusController {
    @Autowired
    private MystemService mystemService;

    @Autowired
    private CorpusUtils corpusUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/analyse")
    public List<Word> analyseText(@RequestBody AnalysePostRequest analysePostRequest, @RequestParam(name = "doReplaces", defaultValue = "false")
            boolean doReplaces) throws IOException, InterruptedException {
        String analysedText;
        String text = analysePostRequest.getText();
        HashMap<Integer, String> textWithReplacesMap;
        List<String> textList = new ArrayList<>();
        if (doReplaces) {
            String[] textArray = text.split("\\b");
            textList = Arrays.asList(textArray);
            textWithReplacesMap = corpusUtils.doReplacesInText(userDetailsService.getUserReplaces(), textList);
            text = String.join(" ", textWithReplacesMap.values());
        }
        analysedText = mystemService.analyseText(text);
        if (doReplaces) {
            analysedText = corpusUtils.revertReplaces(analysedText, textList).toString();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(analysedText, new TypeReference<List<Word>>() {
        });
    }
}
