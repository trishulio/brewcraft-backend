package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;

public class AddUserRoleDto extends BaseDto {
    private Long roleTypeId;

    public AddUserRoleDto() {
    }

    public AddUserRoleDto(Long userRoleTypeId) {
        setRoleTypeId(userRoleTypeId);
    }

    public Long getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
}
