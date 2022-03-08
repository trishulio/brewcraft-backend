package io.company.brewcraft.model;

import java.time.LocalDateTime;

import io.company.brewcraft.service.CrudEntity;

public class IaasTenant extends BaseModel implements UpdateIaasTenant, CrudEntity<String>, Audited {
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

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
        // Not implemented due to lack of use-case
        return -1;
    }

}
