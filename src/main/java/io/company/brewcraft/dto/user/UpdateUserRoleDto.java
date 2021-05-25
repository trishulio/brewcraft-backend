package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;

public class UpdateUserRoleDto extends BaseDto {
    private Long id;
    private Long roleTypeId;
    private Integer version;

    public UpdateUserRoleDto() {
    }

    public UpdateUserRoleDto(Long id) {
        this();
        setId(id);
    }

    public UpdateUserRoleDto(Long id, Long userRoleTypeId, Integer version) {
        this(id);
        setRoleTypeId(userRoleTypeId);
        setVersion(version);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
