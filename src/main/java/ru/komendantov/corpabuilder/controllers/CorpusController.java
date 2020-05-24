package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.services.MystemService;

import java.io.IOException;
import java.util.List;


@RestController("/api/v1/corpus")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CorpusController {
    @Autowired
    private MystemService mystemService;

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/analyse")
    public List<Word> analyseText(@RequestBody String text) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject textJson = new JSONObject(text);
        String analysedText = mystemService.analyseText(textJson.getString("text"));
        return objectMapper.readValue(analysedText, new TypeReference<List<Word>>() {
        });
    }


}
