package io.company.brewcraft.repository.user;

import java.util.Collection;

import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;

public interface EnhancedUserSalutationRepository {
    void refresh(Collection<UserSalutation> salutations);
    
    void refreshAccessors(Collection<? extends UserSalutationAccessor> accessors);
}
