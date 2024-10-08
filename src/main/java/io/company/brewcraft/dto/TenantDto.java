package io.company.brewcraft.dto;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

public class TenantDto extends BaseDto {
    private UUID id;
    private String name;
    private URL url;
    private Boolean isReady;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public TenantDto() {
        super();
    }

    public TenantDto(UUID id) {
        this();
        setId(id);
    }

    public TenantDto(UUID id, String name, URL url, Boolean isReady, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this(id);
        setName(name);
        setUrl(url);
        setIsReady(isReady);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
