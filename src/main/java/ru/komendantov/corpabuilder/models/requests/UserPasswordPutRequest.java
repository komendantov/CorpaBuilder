package ru.komendantov.corpabuilder.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserPasswordPutRequest {
    @NotBlank
    @Size(max = 120)
    private String password;
}
