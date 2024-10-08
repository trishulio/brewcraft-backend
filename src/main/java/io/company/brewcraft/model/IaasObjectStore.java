package io.company.brewcraft.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CrudEntity;

public class IaasObjectStore extends BaseEntity implements UpdateIaasObjectStore, CrudEntity<String>, Audited {
    private static final Logger log = LoggerFactory.getLogger(IaasObjectStore.class);

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public IaasObjectStore() {
        super();
    }

    public IaasObjectStore(String id) {
        this();
        setId(id);
    }

    public IaasObjectStore(String name, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this(name);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
    }

    @Override
    public void setId(String id) {
        setName(id);
    }

    @Override
    public String getId() {
        return getName();
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
        // Versioning not implemented due to lack of use-case
        return null;
    }
}