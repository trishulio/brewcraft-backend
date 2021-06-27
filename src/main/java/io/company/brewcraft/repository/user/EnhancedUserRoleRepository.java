package io.company.brewcraft.repository.user;

import java.util.Collection;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;

public interface EnhancedUserRoleRepository {
    void refresh(Collection<UserRole> roles);

    void refreshAccessors(Collection<? extends UserRoleAccessor> accessors);
}
