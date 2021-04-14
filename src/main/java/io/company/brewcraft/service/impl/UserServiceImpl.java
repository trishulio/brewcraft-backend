package io.company.brewcraft.service.impl;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserIdAndDisplayName;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.repository.UserRepository;
import io.company.brewcraft.security.idp.IdentityProvider;
import io.company.brewcraft.service.UserService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

@Transactional
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final IdentityProvider identityProvider;

    public UserServiceImpl(final UserRepository userRepository, final IdentityProvider identityProvider) {
        this.userRepository = userRepository;
        this.identityProvider = identityProvider;
    }

    @Override
    public Page<User> getUsers(Set<Long> ids, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, int page, int size, Set<String> sort, boolean orderAscending) {

        final Specification<User> userSpecification = SpecificationBuilder
                .builder()
                .in(User.Property.id.name(), ids)
                .in(User.Property.displayName.name(), displayNames)
                .in(User.Property.email.name(), emails)
                .in(User.Property.phoneNumber.name(), phoneNumbers)
                .build();


        return userRepository.findAll(userSpecification, pageRequest(sort, orderAscending, page, size));
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User addUser(User user) {
        final User addedUser = userRepository.save(user);
        identityProvider.createUser(addedUser.getDisplayName(), addedUser.getEmail());
        return addedUser;
    }

    @Override
    public User putUser(Long id, User updatedUser) {
        updatedUser.setId(id);
        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    public User patchUser(Long id, User updatedUser) {
        User existingUser = Optional.ofNullable(getUser(id)).orElseThrow(() -> new EntityNotFoundException("User", id.toString()));
        updatedUser.copyToNullFields(existingUser);
        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        final UserIdAndDisplayName deletingUser = userRepository.findDisplayNameById(id);
        userRepository.deleteById(id);
        identityProvider.deleteUser(deletingUser.getDisplayName());
    }
}
