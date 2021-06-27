package io.company.brewcraft.model.user;

public interface UserAccessor {
    final String ATTR_USER = "user";

    User getUser();
    
    void setUser(User user);
}
