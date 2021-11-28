package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import org.springframework.context.annotation.Lazy;

import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.repository.user.EnhancedUserRoleBindingRepository;
import io.company.brewcraft.repository.user.UserRoleRepository;

public class EnhancedUserRoleBindingRepositoryImpl implements EnhancedUserRoleBindingRepository {

    private UserRoleRepository userRoleRepo;

    public EnhancedUserRoleBindingRepositoryImpl(@Lazy UserRepository userRepo, UserRoleRepository userRoleRepo) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public void refresh(Collection<UserRoleBinding> bindings) {
        userRoleRepo.refreshAccessors(bindings);
    }

    @Override
    public void refreshRoles(Collection<UserRoleBinding> bindings) {
        this.userRoleRepo.refreshAccessors(bindings);
    }
}
