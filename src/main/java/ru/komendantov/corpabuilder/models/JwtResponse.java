package ru.komendantov.corpabuilder.models;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String token;
    //  private String type;
    private String userId;
    private String username;
    private String email;
    private List<String> roles;


    public JwtResponse(String token, String userId, String username, String email, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
