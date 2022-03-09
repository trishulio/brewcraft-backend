package io.company.brewcraft.service.impl.user;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.user.BaseUser;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.user.UserService;

@Transactional
public class UserServiceImpl extends BaseService implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepo;
    private final IdpUserRepository idpRepo;
    private final Refresher<User, UserAccessor> userRefresher;
    private final ContextHolder contextHolder;

    public UserServiceImpl(final UserRepository userRepo, final IdpUserRepository idpRepo, Refresher<User, UserAccessor> userRefresher, ContextHolder contextHolder) {
        this.userRepo = userRepo;
        this.idpRepo = idpRepo;
        this.userRefresher = userRefresher;
        this.contextHolder = contextHolder;
    }

    @Override
    public Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<Long> statusIds, Set<Long> salutationIds, Set<String> roles, int page, int size, SortedSet<String> sort, boolean orderAscending) {

        final Specification<User> userSpecification = WhereClauseBuilder
                .builder()
                .in(User.FIELD_ID, ids)
                .not().in(User.FIELD_ID, excludeIds)
                .in(User.FIELD_USER_NAME, userNames)
                .in(User.FIELD_DISPLAY_NAME, displayNames)
                .in(User.FIELD_EMAIL, emails)
                .in(User.FIELD_PHONE_NUMBER, phoneNumbers)
                .in(new String[]{User.FIELD_STATUS, UserStatus.FIELD_ID }, statusIds)
                .in(new String[]{User.FIELD_SALUTATION, UserStatus.FIELD_ID }, salutationIds)
//                .in(new String[]{User.Property.roles.name(), UserRole.Property.userRole.name(), UserRole.Property.name.name()}, roles)
                .build();

        return userRepo.findAll(userSpecification, pageRequest(sort, orderAscending, page, size));
    }

    @Override
    public User getUser(Long id) {
        log.debug("Retrieving User with Id: {}", id);
        User user = null;
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            log.debug("Found user with id: {}", id);
            user = optional.get();
        }
        return user;
    }

    @Override
    public User addUser(BaseUser<?> addition) {
        log.debug("Attempting to persist user : {}", addition.getEmail());

        User user = new User();
        user.override(addition, getPropertyNames(BaseUser.class));
        userRefresher.refresh(List.of(user));

        User addedUser = userRepo.saveAndFlush(user);

        idpRepo.createUserInTenant(addedUser, contextHolder.getTenantContext().getIaasIdpTenant());

        return addedUser;
    }

    @Override
    public User putUser(final Long userId, final UpdateUser<? extends UpdateUserRole> update) {
        log.debug("Updating the User with Id: {}", userId);

        User existing = getUser(userId);

        if (existing == null) {
            throw new RuntimeException("No existing User found with id: " + userId);
        }

        existing.optimisticLockCheck(update);
        Class<? super User> userClz = UpdateUser.class;

        User temp = new User();
        temp.override(update, getPropertyNames(userClz));
        userRefresher.refresh(List.of(temp));

        existing.override(temp, getPropertyNames(userClz));
        existing.setId(userId);

        User updated = userRepo.saveAndFlush(existing);

        return updated;
    }

    @Override
    public User patchUser(Long userId, UpdateUser<? extends UpdateUserRole> patch) {
        log.debug("Performing Patch on User with Id: {}", userId);

        User existing = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId.toString()));
        existing.optimisticLockCheck(patch);

        User temp = new User();
        temp.override(existing);
        temp.outerJoin(patch, getPropertyNames(UpdateUser.class));
        userRefresher.refresh(List.of(temp));

        existing.override(temp);
        existing.setId(userId);

        return userRepo.saveAndFlush(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        userRepo.deleteById(id);
        idpRepo.deleteUser(user.getEmail());
    }
}