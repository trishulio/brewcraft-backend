package io.company.brewcraft.service.user;

import io.company.brewcraft.model.user.User;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface UserService {

    User getUser(Long id);

    User addUser(User user);

    User putUser(Long id, User user);

    User patchUser(Long id, User user);

    void deleteUser(Long id);

    Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<String> status, Set<String> salutations, Set<String> roles, int page, int size, Set<String> sort, boolean orderAscending);
}
