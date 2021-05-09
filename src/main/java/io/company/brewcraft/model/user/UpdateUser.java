package io.company.brewcraft.model.user;

import io.company.brewcraft.model.Versioned;

public interface UpdateUser<T extends UpdateUserRole<? extends UpdateUserRoleType,? extends UpdateUser<T>>> extends BaseUser<T>, Versioned {
}
