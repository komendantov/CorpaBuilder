package ru.komendantov.corpabuilder.auth.models;

import lombok.Data;

import java.util.HashMap;

@Data
public class UserSettings {
    private HashMap<String, String> replaces = new HashMap<>();
}
