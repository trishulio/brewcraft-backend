package io.company.brewcraft.service;

import io.company.brewcraft.model.user.User;

public interface IdpUserRepository {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
