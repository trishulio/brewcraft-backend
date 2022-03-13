package io.company.brewcraft.model;

import java.time.LocalDateTime;

public interface BaseIaasAuthorization {
    String getAccessKey();

    void setAccessKey(String accessKey);

    String getAccessSecret();

    void setAccessSecret(String accessSecret);

    String getSessionToken();

    void setSessionToken(String sessionToken);

    LocalDateTime getExpiration();

    void setExpiration(LocalDateTime Expiration);
}
