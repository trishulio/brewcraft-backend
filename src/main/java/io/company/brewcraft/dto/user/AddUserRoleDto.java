package io.company.brewcraft.dto.user;


import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.common.FixedTypeDto;

import javax.validation.constraints.NotNull;

public class AddUserRoleDto extends BaseDto implements BaseUserRoleDto {

    @NotNull
    private FixedTypeDto userRoleType;

    @Override
    public FixedTypeDto getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(FixedTypeDto userRoleType) {
        this.userRoleType = userRoleType;
    }
}
