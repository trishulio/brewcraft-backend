package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.common.FixedTypeDto;

public class UserRoleDto extends BaseDto implements BaseUserRoleDto {

    private Long id;

    private FixedTypeDto userRoleType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public FixedTypeDto getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(FixedTypeDto userRoleType) {
        this.userRoleType = userRoleType;
    }
}
