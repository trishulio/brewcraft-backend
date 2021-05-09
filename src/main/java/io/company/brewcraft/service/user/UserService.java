package io.company.brewcraft.service.user;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.user.User;

public interface UserService {

    User getUser(Long id);

    User addUser(User user);

    User putUser(Long id, User user);

    User patchUser(Long id, User user);

    void deleteUser(Long id);

    Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<Long> statusIds, Set<Long> salutationIds, Set<String> roles, int page, int size, Set<String> sort, boolean orderAscending);
}
