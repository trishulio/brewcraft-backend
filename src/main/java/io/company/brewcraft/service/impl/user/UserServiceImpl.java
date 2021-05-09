package io.company.brewcraft.service.impl.user;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class UserServiceImpl extends BaseService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepo;
    private final IdpUserRepository idpRepo;

    private final UtilityProvider utilProvider;

    public UserServiceImpl(final UserRepository userRepo, final IdpUserRepository idpRepo, final UtilityProvider utilProvider) {
        this.userRepo = userRepo;
        this.idpRepo = idpRepo;
        this.utilProvider = utilProvider;
    }

    @Override
    public Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<Long> statusIds, Set<Long> salutationIds, Set<String> roles, int page, int size, Set<String> sort, boolean orderAscending) {

        final Specification<User> userSpecification = SpecificationBuilder
                .builder()
                .in(User.FIELD_ID, ids)
                .not().in(User.FIELD_ID, excludeIds)
                .in(User.FIELD_USER_NAME, userNames)
                .in(User.FIELD_DISPLAY_NAME, displayNames)
                .in(User.FIELD_EMAIL, emails)
                .in(User.FIELD_PHONE_NUMBER, phoneNumbers)
                .in(new String[]{User.FIELD_STATUS, UserStatus.FIELD_ID }, statusIds)
                .in(new String[]{User.FIELD_SALUTATION, UserStatus.FIELD_ID }, salutationIds)
//                .in(new String[]{User.Property.roles.name(), UserRole.Property.userRoleType.name(), UserRoleType.Property.name.name()}, roles)
                .build();

        return userRepo.findAll(userSpecification, pageRequest(sort, orderAscending, page, size));
    }

    @Override
    public User getUser(Long id) {
        logger.debug("Retrieving User with Id: {}", id);
        User user = null;
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            logger.debug("Found user with id: {}", id);
            user = optional.get();
        }
        return user;
    }

    @Override
    public User addUser(User user) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(user != null, "Add User Payload cannot be null");
        validator.raiseErrors();

        logger.debug("Attempting to persist user : {}", user.getUserName());

        userRepo.refresh(Set.of(user));
        User addedUser = userRepo.saveAndFlush(user);

        logger.debug("Attempting to save user : {} in identity provider", user.getUserName());
        idpRepo.createUser(addedUser);
        return addedUser;
    }

    @Override
    public User putUser(final Long id, final User updatingUser) {
        logger.debug("Updating the User with Id: {}", id);
        Validator validator = this.utilProvider.getValidator();
        validator.rule(updatingUser != null, "Update User Payload cannot be null");
        validator.raiseErrors();

        updatingUser.setId(id);
        userRepo.refresh(Set.of(updatingUser));
        final User updatedUser = userRepo.saveAndFlush(updatingUser);
        
        idpRepo.updateUser(updatingUser);

        return updatedUser;
    }

    @Override
    public User patchUser(Long id, User updatingUser) {
        logger.debug("Performing Patch on User with Id: {}", id);
        Validator validator = this.utilProvider.getValidator();
        validator.rule(updatingUser != null, "Update Payload cannot be null");
        validator.raiseErrors();

        User existingUser = Optional.ofNullable(getUser(id)).orElseThrow(() -> new EntityNotFoundException("User", id.toString()));

        updatingUser.copyToNullFields(existingUser);
        userRepo.refresh(Set.of(updatingUser));
        final User updatedUser = userRepo.saveAndFlush(updatingUser);

        idpRepo.updateUser(updatingUser);

        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        userRepo.deleteById(id);
        idpRepo.deleteUser(user);
    }
}
