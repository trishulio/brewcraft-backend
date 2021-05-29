package io.company.brewcraft.model.user;

public interface BaseUserRole {
    final String ATTR_NAME = "name";

    String getName();

    void setName(String name);
}
