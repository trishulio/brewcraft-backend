package io.company.brewcraft.model.user;

public interface BaseUserSalutation {
    final String ATTR_TITLE = "title";

    String getTitle();

    void setTitle(String title);
}
