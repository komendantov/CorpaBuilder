package ru.komendantov.corpabuilder.auth.enums;

public enum ERole {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    public String getName() {
        return this.name();
    }
}