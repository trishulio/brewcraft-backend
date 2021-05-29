package io.company.brewcraft.repository.user;

import java.util.Collection;

import io.company.brewcraft.model.user.UserRoleBinding;

public interface EnhancedUserRoleBindingRepository {
    void refresh(Collection<UserRoleBinding> user);
}
