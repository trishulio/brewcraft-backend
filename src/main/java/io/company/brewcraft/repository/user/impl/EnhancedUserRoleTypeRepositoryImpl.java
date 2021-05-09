package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserRoleTypeAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.EnhancedUserRoleTypeRepository;

public class EnhancedUserRoleTypeRepositoryImpl implements EnhancedUserRoleTypeRepository {
    
    private AccessorRefresher<Long, UserRoleTypeAccessor, Identified<Long>> refresher;

    @Override
    public void refresh(Collection<UserRoleType> roleTypes) {
        // RoleType has no child entity, hence, skipping.
    }

    @Override
    public void refreshAccessors(Collection<? extends UserRoleTypeAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
