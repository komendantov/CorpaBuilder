package ru.komendantov.corpabuilder.auth.models;

import lombok.Data;

import javax.validation.constraints.Min;
//import ru.komendantov.corpabuilder.entity.Role;

@Data
public class SignupRequest {
    private String username;
    private String email;
    @Min(8)
    private String password;
    //  private Role role;
}
