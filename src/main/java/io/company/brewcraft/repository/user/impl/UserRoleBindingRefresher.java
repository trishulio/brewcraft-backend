package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.model.user.UserRoleBindingAccessor;
import io.company.brewcraft.repository.Refresher;

public class UserRoleBindingRefresher implements Refresher<UserRoleBinding, UserRoleBindingAccessor> {
    private final Refresher<UserRole, UserRoleAccessor> userRoleRefresher;

    public UserRoleBindingRefresher(Refresher<UserRole, UserRoleAccessor> userRoleRefresher) {
        this.userRoleRefresher = userRoleRefresher;
    }

    @Override
    public void refresh(Collection<UserRoleBinding> bindings) {
        userRoleRefresher.refreshAccessors(bindings);
    }

    @Override
    public void refreshAccessors(Collection<? extends UserRoleBindingAccessor> accessors) {
        // NOTE: Not needed at this time
    }
}
