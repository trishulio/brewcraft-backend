package io.company.brewcraft.model;

import java.time.LocalDateTime;

public interface BaseIaasAuthorization {
    final String ATTR_ACCESS_KEY = "accessKey";
    final String ATTR_ACCESS_SECRET = "accessSecret";
    final String ATTR_SESSION_TOKEN = "sessionToken";
    final String ATTR_EXPIRATION = "expiration";

    String getAccessKey();

    void setAccessKey(String accessKey);

    String getAccessSecret();

    void setAccessSecret(String accessSecret);

    String getSessionToken();

    void setSessionToken(String sessionToken);

    LocalDateTime getExpiration();

    void setExpiration(LocalDateTime Expiration);
}
