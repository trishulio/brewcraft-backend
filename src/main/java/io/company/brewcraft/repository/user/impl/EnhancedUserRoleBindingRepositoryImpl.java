package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.repository.user.EnhancedUserRoleBindingRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.repository.user.UserRoleRepository;

public class EnhancedUserRoleBindingRepositoryImpl implements EnhancedUserRoleBindingRepository {

    private UserRepository userRepo;
    private UserRoleRepository userRoleRepo;

    public EnhancedUserRoleBindingRepositoryImpl(UserRepository userRepo, UserRoleRepository userRoleRepo) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public void refresh(Collection<UserRoleBinding> bindings) {
        this.userRepo.refreshAccessors(bindings);
        refreshRoles(bindings);
    }

    @Override
    public void refreshRoles(Collection<UserRoleBinding> bindings) {
        this.userRoleRepo.refreshAccessors(bindings);
    }
}
