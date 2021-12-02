package io.company.brewcraft.repository.user.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.model.user.UserRoleBindingAccessor;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.Refresher;

public class UserRefresher implements Refresher<User, UserAccessor> {
    private static final Logger log = LoggerFactory.getLogger(UserRefresher.class);

    private final AccessorRefresher<Long, UserAccessor, User> refresher;
    private final Refresher<UserStatus, UserStatusAccessor> statusRefresher;
    private final Refresher<UserSalutation, UserSalutationAccessor> salutationRefresher;
    private final Refresher<UserRoleBinding, UserRoleBindingAccessor> roleBindingRefresher;

    @Autowired
    public UserRefresher(AccessorRefresher<Long, UserAccessor, User> refresher, Refresher<UserStatus, UserStatusAccessor> statusRefresher, Refresher<UserSalutation, UserSalutationAccessor> salutationRefresher, Refresher<UserRoleBinding, UserRoleBindingAccessor> roleBindingRefresher) {
        this.refresher = refresher;
        this.statusRefresher = statusRefresher;
        this.salutationRefresher = salutationRefresher;
        this.roleBindingRefresher = roleBindingRefresher;
    }

    @Override
    public void refresh(Collection<User> users) {
        this.statusRefresher.refreshAccessors(users);
        this.salutationRefresher.refreshAccessors(users);

        List<UserRoleBinding> bindings = users.stream().filter(u -> u != null && u.getRoleBindings() != null && u.getRoleBindings().size() > 0).flatMap(u -> u.getRoleBindings().stream()).collect(Collectors.toList());
        this.roleBindingRefresher.refresh(bindings);
    }

    @Override
    public void refreshAccessors(Collection<? extends UserAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }
}