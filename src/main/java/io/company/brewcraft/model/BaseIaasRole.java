package io.company.brewcraft.model;

public interface BaseIaasRole {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getAssumePolicyDocument();

    void setAssumePolicyDocument(String assumePolicyDocument);
}
