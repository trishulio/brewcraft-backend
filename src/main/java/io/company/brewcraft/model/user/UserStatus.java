package io.company.brewcraft.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getUserStatusName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
