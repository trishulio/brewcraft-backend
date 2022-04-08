package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class IaasAuthorizationDto extends BaseDto {
    private String accessKey;
    private String accessSecret;
    private String sessionToken;
    private LocalDateTime expiration;

    public IaasAuthorizationDto() {
        super();
    }

    public IaasAuthorizationDto(String accessKey, String accessSecret, String sessionToken, LocalDateTime expiration) {
        setAccessKey(accessKey);
        setAccessSecret(accessSecret);
        setSessionToken(sessionToken);
        setExpiration(expiration);
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
