package ru.komendantov.corpabuilder.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserUpdateUsernamePutRequest {
    @NotBlank
    @Size(max = 20)
    private String username;
}
