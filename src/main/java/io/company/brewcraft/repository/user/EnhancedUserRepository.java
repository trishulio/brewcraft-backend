package io.company.brewcraft.repository.user;


import java.util.Collection;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.UserAccessor;

public interface EnhancedUserRepository {

    User mapAndSave(User user);
    
    void refreshAccessors(Collection<? extends UserAccessor> accessors);

}
