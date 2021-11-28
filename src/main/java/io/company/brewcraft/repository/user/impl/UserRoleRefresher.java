package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.Refresher;

public class UserRoleRefresher implements Refresher<UserRole, UserRoleAccessor> {
    private AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher;

    public UserRoleRefresher(AccessorRefresher<Long, UserRoleAccessor, UserRole> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<UserRole> roles) {
        // Role has no child entity, hence, skipping.
    }

    @Override
    public void refreshAccessors(Collection<? extends UserRoleAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
