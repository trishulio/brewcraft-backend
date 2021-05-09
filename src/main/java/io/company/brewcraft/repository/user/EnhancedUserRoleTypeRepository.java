package io.company.brewcraft.repository.user;

import java.util.Collection;

import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserRoleTypeAccessor;

public interface EnhancedUserRoleTypeRepository {
    void refresh(Collection<UserRoleType> roleTypes);
    
    void refreshAccessors(Collection<? extends UserRoleTypeAccessor> accessors);
}
