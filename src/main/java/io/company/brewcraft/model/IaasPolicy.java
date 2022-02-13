package io.company.brewcraft.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CrudEntity;

public class IaasPolicy extends BaseEntity implements UpdateIaasPolicy, CrudEntity<String>, Audited {
    private static final Logger log = LoggerFactory.getLogger(IaasPolicy.class);

    private String name;
    private String document;
    private String description;
    private String iaasResourceName;
    private String iaasId;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public IaasPolicy() {
        super();
    }

    public IaasPolicy(String id) {
        this();
        setId(id);
    }

    public IaasPolicy(String id, String document) {
        this(id);
        setDocument(document);
    }

    @Override
    public String getId() {
        return getIaasResourceName();
    }

    @Override
    public void setId(String id) {
        setIaasResourceName(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getIaasId() {
        return iaasId;
    }

    public void setIaasId(String iaasId) {
        this.iaasId = iaasId;
    }

    public String getIaasResourceName() {
        return iaasResourceName;
    }

    public void setIaasResourceName(String iaasResourceName) {
        this.iaasResourceName = iaasResourceName;
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        // TODO: Implement versioning if the entity is persisted in DB.
        // Currently, the entities don't have a use-case for versioning.
        // Hence, it's not implemented.
        return -1;
    }
}
