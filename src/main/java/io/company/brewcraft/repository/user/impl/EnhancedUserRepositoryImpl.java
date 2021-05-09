package io.company.brewcraft.repository.user.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.user.EnhancedUserRepository;
import io.company.brewcraft.repository.user.UserRoleRepository;
import io.company.brewcraft.repository.user.UserSalutationRepository;
import io.company.brewcraft.repository.user.UserStatusRepository;

public class EnhancedUserRepositoryImpl implements EnhancedUserRepository {
    private static final Logger logger = LoggerFactory.getLogger(EnhancedUserRepositoryImpl.class);

    private final AccessorRefresher<Long, UserAccessor, Identified<Long>> refresher;
    private final UserStatusRepository statusRepo;
    private final UserSalutationRepository salutationRepo;
    private final UserRoleRepository roleRepo;

    @Autowired
    public EnhancedUserRepositoryImpl(AccessorRefresher<Long, UserAccessor, Identified<Long>> refresher, UserStatusRepository statusRepo, UserSalutationRepository salutationRepo, UserRoleRepository roleRepo) {
        this.refresher = refresher;
        this.statusRepo = statusRepo;
        this.salutationRepo = salutationRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public void refresh(Collection<User> users) {
        this.statusRepo.refreshAccessors(users);
        this.salutationRepo.refreshAccessors(users);
        List<UserRole> roles = users == null ? null : users.stream().filter(u -> u != null && u.getRoles() != null && u.getRoles().size() > 0).flatMap(u -> u.getRoles().stream()).collect(Collectors.toList());
        this.roleRepo.refresh(roles);        
    }

    @Override
    public void refreshAccessors(Collection<? extends UserAccessor> accessors) {
        refresher.refreshAccessors(accessors);
    }
}
