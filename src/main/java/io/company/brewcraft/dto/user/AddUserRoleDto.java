package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.BaseDto;

public class AddUserRoleDto extends BaseDto {
    private String name;

    public AddUserRoleDto() {

    }

    public AddUserRoleDto(String name) {
        this();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
