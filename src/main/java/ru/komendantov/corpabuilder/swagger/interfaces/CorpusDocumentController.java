package ru.komendantov.corpabuilder.swagger.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.komendantov.corpabuilder.models.Word;
import ru.komendantov.corpabuilder.models.document.CorpusDocument;
import ru.komendantov.corpabuilder.models.document.SearchResult;
import ru.komendantov.corpabuilder.models.requests.AnalysePostRequest;
import ru.komendantov.corpabuilder.models.requests.SearchRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "CorpusDocument", tags = "document")
public interface CorpusDocumentController {
    @ApiOperation(value = "Анализ текста", authorizations = {@Authorization(value = "Bearer")},
            notes = "Проанализировать переданный текст")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    List<Word> analyseText(@RequestBody @ApiParam("Анализируемый текст") AnalysePostRequest analysePostRequest,
                           @ApiParam(value = "Заменить устаревшие символы", defaultValue = "false")
                                   boolean doReplaces) throws IOException, InterruptedException;

    @ApiOperation(value = "Сохранить документ", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ok")
    })
    ResponseEntity<?> saveCorpusDocument(@RequestBody @ApiParam("корпус") CorpusDocument documentWords);

    @ApiOperation(value = "Поиск по документам", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    List<SearchResult> search(@RequestBody SearchRequest searchRequest, Integer page);

    @ApiOperation(value = "Получить документ по ID", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    ResponseEntity getCorpusDocument(@ApiParam String id);

    @ApiOperation(value = "Получить документы пользователя", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    ArrayList<SearchResult> getUserDocuments();

    @ApiOperation(value = "Изменить документ", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    ResponseEntity<?> changeCorpus(@ApiParam("id корпуса") String documentId);

    @ApiOperation(value = "Удалить документ", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    ResponseEntity<?> deleteCorpus(@ApiParam("id документа") String documentId);
}