package ru.komendantov.corpabuilder.auth.models;

import lombok.Data;
//import ru.komendantov.corpabuilder.entity.Role;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    //  private Role role;
}
