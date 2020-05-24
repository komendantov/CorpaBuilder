package ru.komendantov.corpabuilder.models.mystem;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Text {
    private String title = StringUtils.EMPTY;
    private Integer authorID = 0;
    private DateTime creationDate = new DateTime();
    private List<Word> words = new ArrayList<>();
}
