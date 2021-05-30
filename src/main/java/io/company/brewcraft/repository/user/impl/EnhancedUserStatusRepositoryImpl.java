package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.EnhancedUserStatusRepository;

public class EnhancedUserStatusRepositoryImpl implements EnhancedUserStatusRepository {

    private AccessorRefresher<Long, UserStatusAccessor, Identified<Long>> refresher;
    
    public EnhancedUserStatusRepositoryImpl(AccessorRefresher<Long, UserStatusAccessor, Identified<Long>> refresher) {
        this.refresher = refresher;
    }
    
    @Override
    public void refresh(Collection<UserStatus> statuses) {
        // No child entities, hence skipping.
    }

    @Override
    public void refreshAccessors(Collection<? extends UserStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
