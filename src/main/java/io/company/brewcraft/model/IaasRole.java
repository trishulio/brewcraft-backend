package io.company.brewcraft.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CrudEntity;

public class IaasRole extends BaseEntity implements UpdateIaasRole, CrudEntity<String>, Audited {
    private static final Logger log = LoggerFactory.getLogger(IaasRole.class);

    private String name;
    private String description;
    private String assumePolicyDocument;
    private String iaasResourceName;
    private String iaasId;
    private LocalDateTime lastUsed;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    
    @Override
    public String getId() {
        return getName();
    }

    @Override
    public void setId(String id) {
        setName(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public String getAssumePolicyDocument() {
        return assumePolicyDocument;
    }

    @Override
    public void setAssumePolicyDocument(String assumePolicyDocument) {
        this.assumePolicyDocument = assumePolicyDocument;
    }

    public String getIaasResourceName() {
        return iaasResourceName;
    }

    public void setIaasResourceName(String iaasResourceName) {
        this.iaasResourceName = iaasResourceName;
    }

    public String getIaasId() {
        return iaasId;
    }

    public void setIaasId(String iaasId) {
        this.iaasId = iaasId;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Integer getVersion() {
        // TODO: The versioning is not implemented due to lack of use-case.
        return -1;
    }
}