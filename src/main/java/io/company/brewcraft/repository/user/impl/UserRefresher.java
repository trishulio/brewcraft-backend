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
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.EnhancedUserRepository;
import io.company.brewcraft.repository.user.UserRoleBindingRepository;
import io.company.brewcraft.repository.user.UserSalutationRepository;
import io.company.brewcraft.repository.user.UserStatusRepository;

public class UserRefresher implements EnhancedUserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRefresher.class);

    private final AccessorRefresher<Long, UserAccessor, User> refresher;
    private final UserStatusRepository statusRepo;
    private final UserSalutationRepository salutationRepo;
    private final UserRoleBindingRepository roleBindingRepo;

    @Autowired
    public UserRefresher(AccessorRefresher<Long, UserAccessor, User> refresher, UserStatusRepository statusRepo, UserSalutationRepository salutationRepo, UserRoleBindingRepository roleBindingRepo) {
        this.refresher = refresher;
        this.statusRepo = statusRepo;
        this.salutationRepo = salutationRepo;
        this.roleBindingRepo = roleBindingRepo;
    }

    @Override
    public void refresh(Collection<User> users) {
        this.statusRepo.refreshAccessors(users);
        this.salutationRepo.refreshAccessors(users);

        List<UserRoleBinding> bindings = users.stream().filter(u -> u != null && u.getRoleBindings() != null && u.getRoleBindings().size() > 0).flatMap(u -> u.getRoleBindings().stream()).collect(Collectors.toList());
        this.roleBindingRepo.refresh(bindings);
    }

    @Override
    public void refreshAccessors(Collection<? extends UserAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }
}