package io.company.brewcraft.dto.user;

public class AddUserRoleDto {
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
