package io.company.brewcraft.repository.user;

import java.util.Collection;

import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;

public interface EnhancedUserStatusRepository {
    void refresh(Collection<UserStatus> statuses);

    void refreshAccessors(Collection<? extends UserStatusAccessor> accessors);
}
