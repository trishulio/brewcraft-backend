package io.company.brewcraft.repository.user.impl;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.user.EnhancedUserRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.repository.user.UserRoleTypeRepository;
import io.company.brewcraft.repository.user.UserSalutationRepository;
import io.company.brewcraft.repository.user.UserStatusRepository;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class EnhancedUserRepositoryImpl implements EnhancedUserRepository {

    private static final Logger logger = LoggerFactory.getLogger(EnhancedUserRepositoryImpl.class);

    private final UserRepository userRepository;

    private final UserStatusRepository userStatusRepository;

    private final UserSalutationRepository userSalutationRepository;

    private final UserRoleTypeRepository userRoleTypeRepository;


    @Autowired
    public EnhancedUserRepositoryImpl(UserRepository userRepository, UserStatusRepository userStatusRepository, UserSalutationRepository userSalutationRepository, UserRoleTypeRepository userRoleTypeRepository) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
        this.userSalutationRepository = userSalutationRepository;
        this.userRoleTypeRepository = userRoleTypeRepository;
    }

    @Override
    public User mapAndSave(final User user) {

        final String statusName;
        if (Objects.nonNull(user.getStatus()) && StringUtils.isNotEmpty(user.getStatus().getName())) {
            statusName = user.getStatus().getName();
        } else {
            statusName = UserStatus.DEFAULT_STATUS_NAME;
        }

        logger.debug("Target User Status Name: {} of User {}", statusName, user.getUserName());
        UserStatus status = userStatusRepository.findByName(statusName).orElseThrow(() -> new EntityNotFoundException("UserStatus", "name", statusName));
        user.setStatus(status);

        if (Objects.nonNull(user.getSalutation())) {
            if (StringUtils.isNotEmpty(user.getSalutation().getName())) {
                final String salutationName = user.getSalutation().getName();
                logger.debug("Target User Salutation Name: {} of User {}", salutationName, user.getUserName());
                final UserSalutation userSalutation = userSalutationRepository.findByName(salutationName).orElseThrow(() -> new EntityNotFoundException("UserSalutation", "name", salutationName));
                user.setSalutation(userSalutation);
            } else {
                throw new IllegalArgumentException("name field cannot be null or empty in user " + user.getUserName() + " Salutation");
            }
        }

        final List<UserRole> userRoles = user.getRoles();
        if (Objects.nonNull(userRoles)) {
            userRoles.forEach(userRole -> userRole.setUserRoleType(getMappedUserRoleType(userRole.getUserRoleType(), user.getUserName())));
        }
        return userRepository.saveAndFlush(user);
    }

    private UserRoleType getMappedUserRoleType(final UserRoleType userRoleType, final String userName) {
        if (Objects.nonNull(userRoleType) && StringUtils.isNotEmpty(userRoleType.getName())) {
            final String roleTypeName = userRoleType.getName();
            logger.debug("Target User Role Type Name: {} of User {}", roleTypeName, userName);
            return userRoleTypeRepository.findByName(roleTypeName).orElseThrow(() -> new EntityNotFoundException("UserRoleType", "name", roleTypeName));
        } else {
            throw new IllegalArgumentException("user role type or name field of user role type cannot be null or empty in user " + userName + " Role");
        }
    }
}
