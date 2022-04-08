package io.company.brewcraft.model;

public interface BaseIaasObjectStore {
    final String ATTR_NAME = "name";

    String getName();

    void setName(String name);
}
