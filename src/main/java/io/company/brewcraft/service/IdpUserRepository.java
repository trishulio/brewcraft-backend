package io.company.brewcraft.service;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.user.Group;

public interface IdpUserRepository {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    void addUserToGroup(User user, String group);

    void removeUserFromGroup(User user, String group);

    void createUserGroup(Group group);

    void deleteUserGroup(String group);

    boolean userGroupExists(String group);

    void putUserGroup(Group group);

    void createUserInGroup(User user, String group);
}
