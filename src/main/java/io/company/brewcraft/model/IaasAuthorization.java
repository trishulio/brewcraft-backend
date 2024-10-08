package io.company.brewcraft.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CrudEntity;

public class IaasAuthorization extends BaseModel implements UpdateIaasAuthorization, CrudEntity<String> {
    private static final Logger log = LoggerFactory.getLogger(IaasAuthorization.class);

    private String accessKeyId;
    private String accessSecretKey;
    private String sessionToken;
    private LocalDateTime expiration;

    public IaasAuthorization() {
        super();
    }

    public IaasAuthorization(String accessKeyId, String accessSecretKey, String sessionToken, LocalDateTime expiration) {
        this();
        setAccessKeyId(accessKeyId);
        setAccessSecretKey(accessSecretKey);
        setSessionToken(sessionToken);
        setExpiration(expiration);
    }

    @Override
    public String getId() {
        return getAccessKeyId();
    }

    @Override
    public void setId(String id) {
        setAccessKeyId(id);
    }

    @Override
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @Override
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    @Override
    public String getAccessSecretKey() {
        return accessSecretKey;
    }

    @Override
    public void setAccessSecretKey(String accessSecretKey) {
        this.accessSecretKey = accessSecretKey;
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
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}
