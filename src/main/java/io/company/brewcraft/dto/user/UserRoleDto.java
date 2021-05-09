package io.company.brewcraft.dto.user;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;

public class UserRoleDto extends BaseDto {
    private Long id;
    private UserRoleTypeDto roleType;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public UserRoleDto() {
    }

    public UserRoleDto(Long id) {
        setId(id);
    }

    public UserRoleDto(Long id, UserRoleTypeDto roleType, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setRoleType(roleType);
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

    public UserRoleTypeDto getRoleType() {
        return roleType;
    }

    public void setRoleType(UserRoleTypeDto roleType) {
        this.roleType = roleType;
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
