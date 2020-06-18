package ru.komendantov.corpabuilder.models.document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Data
@Document(collection = "documentes")
public class CorpusDocument {
    private String title = StringUtils.EMPTY;
    private String authorID = "";
    private DateTime creationDate = new DateTime();
    private List<DocumentWord> words = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
}