package io.company.brewcraft.dto.user;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;

public class UserStatusDto extends BaseDto {
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;

    public UserStatusDto() {
    }

    public UserStatusDto(Long id) {
        setId(id);
    }

    public UserStatusDto(Long id, String name, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, Integer version) {
        this(id);
        setName(name);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
