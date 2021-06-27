package io.company.brewcraft.repository.user;


import java.util.Collection;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;

public interface EnhancedUserRepository {
    void refresh(Collection<User> user);
    void refreshAccessors(Collection<? extends UserAccessor> accessors);
}
