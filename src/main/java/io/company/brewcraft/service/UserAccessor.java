package io.company.brewcraft.service;

import io.company.brewcraft.model.user.User;

public interface UserAccessor {
    final String ATTR_USER= "user";

    User getUser();
    
    void setUser(User user);
}
