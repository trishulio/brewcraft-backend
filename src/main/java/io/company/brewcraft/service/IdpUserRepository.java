package io.company.brewcraft.service;

import io.company.brewcraft.model.user.User;

public interface IdpUserRepository {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    void addUserToGroup(User user, String group);

    void removeUserFromGroup(User user, String group);

    void createUserGroup(String group);

    void deleteUserGroup(String group);

    boolean groupExists(String group);

    void createUserInGroup(User user, String group);
}
