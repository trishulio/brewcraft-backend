package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;

public class AddUserRoleDto extends BaseDto {
    private Long roleTypeId;
    private Long userId;

    public AddUserRoleDto() {
    }

    public AddUserRoleDto(Long userRoleTypeId, Long userId) {
        setRoleTypeId(userRoleTypeId);
        setUserId(userId);
    }

    public Long getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
