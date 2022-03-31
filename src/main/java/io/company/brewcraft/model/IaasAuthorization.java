package io.company.brewcraft.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.CrudEntity;

public class IaasAuthorization extends BaseModel implements UpdateIaasAuthorization, CrudEntity<String> {
    private static final Logger log = LoggerFactory.getLogger(IaasAuthorization.class);

    private String accessKey;
    private String accessSecret;
    private String sessionToken;
    private LocalDateTime expiration;

    public IaasAuthorization() {
        super();
    }

    public IaasAuthorization(String accessKey, String accessSecret, String sessionToken, LocalDateTime expiration) {
        this();
        setAccessKey(accessKey);
        setAccessSecret(accessSecret);
        setSessionToken(sessionToken);
        setExpiration(expiration);
    }

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
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}
