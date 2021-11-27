package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.EnhancedUserSalutationRepository;

public class UserSalutationRefresher implements EnhancedUserSalutationRepository {

    private AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> refresher;

    public UserSalutationRefresher (AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<UserSalutation> salutations) {
        // No child entity to refresh, hence skipping
    }

    @Override
    public void refreshAccessors(Collection<? extends UserSalutationAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }
}
