package io.company.brewcraft.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CrudEntity;

public class IaasAuthorization extends BaseEntity implements UpdateIaasAuthorization, CrudEntity<String>, Audited {
    private static final Logger log = LoggerFactory.getLogger(IaasAuthorization.class);

    private String accessKey;
    private String accessSecret;
    private String sessionToken;
    private LocalDateTime expiration;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    @Override
    public String getId() {
        return getAccessKey();
    }

    @Override
    public void setId(String id) {
        setAccessKey(id);
    }

    @Override
    public String getAccessKey() {
        return accessKey;
    }

    @Override
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public String getAccessSecret() {
        return accessSecret;
    }

    @Override
    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    @Override
    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public LocalDateTime getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
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
        return -1; // Versioning not implemented due to lack of use-case
    }
}
