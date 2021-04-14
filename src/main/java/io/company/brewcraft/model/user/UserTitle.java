package io.company.brewcraft.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTitle {
    ADMIN("Admin"),
    BREWMASTER("Brewmaster");

    private final String name;

    UserTitle(String name) {
        this.name = name;
    }

    @JsonValue
    public String getUserTitleName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
