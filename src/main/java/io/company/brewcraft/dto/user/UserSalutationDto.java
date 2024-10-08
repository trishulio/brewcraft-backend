package io.company.brewcraft.dto.user;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;

public class UserSalutationDto extends BaseDto {
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;

    public UserSalutationDto() {
    }

    public UserSalutationDto(Long id) {
        setId(id);
    }

    public UserSalutationDto(Long id, String title, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setTitle(title);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
