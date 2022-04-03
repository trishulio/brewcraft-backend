package io.company.brewcraft.service;

import io.company.brewcraft.model.user.User;

public interface OwnedByAccessor {
    final String ATTR_OWNED_BY = "ownedBy";

    User getOwnedBy();

    void setOwnedBy(User user);
}
