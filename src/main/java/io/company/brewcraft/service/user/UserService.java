package io.company.brewcraft.service.user;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.user.BaseUser;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.User;

public interface UserService {

    User getUser(Long userId);

    User addUser(BaseUser<?> addition);

    User putUser(Long userId, UpdateUser<? extends UpdateUserRole> update);

    User patchUser(Long userId, UpdateUser<? extends UpdateUserRole> patch);

    void deleteUser(Long userId);

    Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames, Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<Long> statusIds, Set<Long> salutationIds, Set<String> roles, int page, int size, SortedSet<String> sort, boolean orderAscending);
}
