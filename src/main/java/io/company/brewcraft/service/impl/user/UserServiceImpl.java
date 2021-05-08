package io.company.brewcraft.service.impl.user;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.idp.UserAttributeType;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;
import static io.company.brewcraft.security.session.CognitoPrincipalContext.ATTRIBUTE_EMAIL;
import static io.company.brewcraft.security.session.CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED;

@Transactional
public class UserServiceImpl extends BaseService implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final IdentityProviderClient identityProviderClient;

    private final UtilityProvider utilProvider;

    public UserServiceImpl(final UserRepository userRepository, final IdentityProviderClient identityProviderClient, final UtilityProvider utilProvider) {
        this.userRepository = userRepository;
        this.identityProviderClient = identityProviderClient;
        this.utilProvider = utilProvider;
    }

    @Override
    public Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<String> status, Set<String> salutations, Set<String> roles, int page, int size, Set<String> sort, boolean orderAscending) {

        final Specification<User> userSpecification = SpecificationBuilder
                .builder()
                .in(User.Property.id.name(), ids)
                .not().in(User.Property.id.name(), excludeIds)
                .in(User.Property.userName.name(), userNames)
                .in(User.Property.displayName.name(), displayNames)
                .in(User.Property.email.name(), emails)
                .in(User.Property.phoneNumber.name(), phoneNumbers)
                .in(new String[]{User.Property.status.name(), UserStatus.Property.name.name()}, status)
                .in(new String[]{User.Property.salutation.name(), UserSalutation.Property.name.name()}, salutations)
//                .in(new String[]{User.Property.roles.name(), UserRole.Property.userRoleType.name(), UserRoleType.Property.name.name()}, roles)
                .build();

        return userRepository.findAll(userSpecification, pageRequest(sort, orderAscending, page, size));
    }

    @Override
    public User getUser(Long id) {
        logger.debug("Retrieving User with Id: {}", id);
        User user = null;
        Optional<User> optional = userRepository.findById(id);
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

        final User addedUser = userRepository.mapAndSave(user);

        logger.debug("Attempting to save user : {} in identity provider", user.getUserName());
        createUserInIdentityProvider(addedUser);
        return addedUser;
    }

    @Override
    public User putUser(final Long id, final User updatingUser) {
        logger.debug("Updating the User with Id: {}", id);
        Validator validator = this.utilProvider.getValidator();
        validator.rule(updatingUser != null, "Update User Payload cannot be null");
        validator.raiseErrors();

        final boolean emailUpdated = isEmailUpdated(id, updatingUser.getEmail());

        updatingUser.setId(id);
        final User updatedUser = userRepository.mapAndSave(updatingUser);
        if (emailUpdated) {
            updateEmailAttributeInIdentityProvider(id, updatedUser.getEmail());
        }
        return updatedUser;
    }

    @Override
    public User patchUser(Long id, User updatingUser) {
        logger.debug("Performing Patch on User with Id: {}", id);
        Validator validator = this.utilProvider.getValidator();
        validator.rule(updatingUser != null, "Update Payload cannot be null");
        validator.raiseErrors();

        User existingUser = Optional.ofNullable(getUser(id)).orElseThrow(() -> new EntityNotFoundException("User", id.toString()));

        final boolean emailUpdated = StringUtils.isNoneEmpty(updatingUser.getEmail()) && !existingUser.getEmail().equalsIgnoreCase(updatingUser.getEmail());

        updatingUser.copyToNullFields(existingUser);
        final User updatedUser = userRepository.mapAndSave(updatingUser);
        if (emailUpdated) {
            updateEmailAttributeInIdentityProvider(id, updatedUser.getEmail());
        }
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        logger.debug("Attempting to delete Invoice with Id: {}", id);
        final String deletingUserName = userRepository.findUserNamePerId(id).orElseThrow(() -> new EntityNotFoundException("User", id));;
        userRepository.deleteById(id);
        identityProviderClient.deleteUser(deletingUserName);
    }

    private void createUserInIdentityProvider(final User createdUser) {
        final List<UserAttributeType> userAttributeTypes = new ArrayList<>();
        userAttributeTypes.add(getUserAttributeType(ATTRIBUTE_EMAIL, createdUser.getEmail()));
        userAttributeTypes.add(getUserAttributeType(ATTRIBUTE_EMAIL_VERIFIED, "true"));
        identityProviderClient.createUser(createdUser.getUserName(), userAttributeTypes);
    }

    private boolean isEmailUpdated(final Long userId, final String newEmail) {
        boolean emailUpdated = false;
        if (StringUtils.isNotEmpty(newEmail)) {
            final String userEmail = userRepository.findEmailPerId(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
            emailUpdated = !userEmail.equalsIgnoreCase(newEmail);
        }
        return emailUpdated;
    }

    private void updateEmailAttributeInIdentityProvider(final Long userId, final String email) {
        final String userNameById = userRepository.findUserNamePerId(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));;
        logger.debug("Attempting to update email of user : {} in identity provider", userNameById);
        identityProviderClient.updateUser(userNameById, Collections.singletonList(getUserAttributeType(ATTRIBUTE_EMAIL, email)));
    }

    private UserAttributeType getUserAttributeType(final String name, final String value) {
        return new UserAttributeType(name, value);
    }
}
