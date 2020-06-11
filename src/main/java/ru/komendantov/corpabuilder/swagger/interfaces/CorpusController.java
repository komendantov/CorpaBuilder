package ru.komendantov.corpabuilder.swagger.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.models.corpus.Corpus;
import ru.komendantov.corpabuilder.models.corpus.SearchResult;
import ru.komendantov.corpabuilder.models.requests.AnalysePostRequest;
import ru.komendantov.corpabuilder.models.requests.SearchRequest;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@Api(value = "Corpus", tags = "corpus")
public interface CorpusController {
    @ApiOperation(value = "Анализ текста", authorizations = {@Authorization(value = "Bearer")},
            notes = "Проанализировать переданный текст")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    List<Word> analyseText(@RequestBody @ApiParam("Анализируемый текст") AnalysePostRequest analysePostRequest,
                           @ApiParam(value = "Заменить устаревшие символы", defaultValue = "false")
                                   boolean doReplaces) throws IOException, InterruptedException;

    @ApiOperation(value = "Сохранить корпус", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ok")
    })
    ResponseEntity<?> saveCorpus(@RequestBody @ApiParam("корпус") Corpus corpus);

    @ApiOperation(value = "Поиск по корпусам", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    SearchResult search(@RequestBody SearchRequest searchRequest, Pageable page);
}