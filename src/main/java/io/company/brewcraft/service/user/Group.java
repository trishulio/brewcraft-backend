package io.company.brewcraft.service.user;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.service.CrudEntity;

public class Group extends BaseEntity implements UpdateGroup, CrudEntity<String>, Audited {
    private static final Logger log = LoggerFactory.getLogger(Group.class);
    
    public Group() {
        super();
    }
    
    public Group(String id) {
        this();
        setId(id);
    }

    private String name;
    private IaasRole iaasRole;
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
    public IaasRole getIaasRole() {
        return iaasRole;
    }

    @Override
    public void setIaasRole(IaasRole iaasRole) {
        this.iaasRole = iaasRole;
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
        // Versioning is not implemented due to lack of persistence
        return -1;
    }
}
