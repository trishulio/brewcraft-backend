package io.company.brewcraft.repository.user.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.EnhancedUserRoleRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.repository.user.UserRoleTypeRepository;

public class EnhancedUserRoleRepositoryImpl implements EnhancedUserRoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(EnhancedUserRoleRepositoryImpl.class);

    private AccessorRefresher<Long, UserRoleAccessor, Identified<Long>> refresher;
    private UserRepository userRepo;
    private UserRoleTypeRepository roleTypeRepo;

    public EnhancedUserRoleRepositoryImpl(UserRepository userRepo, UserRoleTypeRepository roleTypeRepo) {
        this.userRepo = userRepo;
        this.roleTypeRepo = roleTypeRepo;
    }

    @Override
    public void refresh(Collection<UserRole> roles) {
        this.userRepo.refreshAccessors(roles);
        this.roleTypeRepo.refreshAccessors(roles);
    }

    @Override
    public void refreshAccessors(Collection<? extends UserRoleAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
