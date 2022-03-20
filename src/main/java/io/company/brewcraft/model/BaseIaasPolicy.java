package io.company.brewcraft.model;

public interface BaseIaasPolicy {
    String getName();

    void setName(String name);

    String getDocument();

    void setDocument(String document);

    String getDescription();

    void setDescription(String description);

    String getIaasId();

    void setIaasId(String iaasId);

    String getIaasResourceName();

    void setIaasResourceName(String iaasResourceName);
}
