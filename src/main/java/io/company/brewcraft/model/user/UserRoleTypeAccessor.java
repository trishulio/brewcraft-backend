package io.company.brewcraft.model.user;

public interface UserRoleTypeAccessor {
    final String ATTR_ROLE_TYPE = "roleType";

    UserRoleType getRoleType();

    void setRoleType(UserRoleType roleType);
}
