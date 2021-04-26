package io.company.brewcraft.repository.user;


import io.company.brewcraft.model.user.User;

public interface EnhancedUserRepository {

    User mapAndSave(User user);
}
