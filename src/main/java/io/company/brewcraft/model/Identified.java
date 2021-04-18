package io.company.brewcraft.model;

public interface Identified {
    final String FIELD_ID = "id";

    Long getId();

    void setId(Long id);
}
