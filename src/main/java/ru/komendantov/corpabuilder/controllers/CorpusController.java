package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.services.MystemService;

import java.io.IOException;
import java.util.List;

@RestController("/api/v1/corpus")
public class CorpusController {
    @Autowired
    private MystemService mystemService;

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/analyse")
    public List<Word> analyseText(@RequestParam(name = "text") String text) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String analysedText = mystemService.analyseText(text);
        return objectMapper.readValue(analysedText, new TypeReference<List<Word>>() {
        });
    }


}
