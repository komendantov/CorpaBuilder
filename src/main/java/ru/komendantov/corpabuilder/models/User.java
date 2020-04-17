package ru.komendantov.corpabuilder.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="user")
public class User {

    private String login;
    private String password;
    private String token;
}
