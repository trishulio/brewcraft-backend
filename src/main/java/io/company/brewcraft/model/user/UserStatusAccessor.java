package io.company.brewcraft.model.user;

public interface UserStatusAccessor {
    final String ATTR_STATUS = "status";

    UserStatus getStatus();

    void setStatus(UserStatus status);
}
