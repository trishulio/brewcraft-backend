package io.company.brewcraft.model.user;

import io.company.brewcraft.model.Versioned;

public interface UpdateUserRole<RT extends UpdateUserRoleType, U extends UpdateUser<? extends UpdateUserRole<RT, U>>> extends BaseUserRole<RT, U>, Versioned {
}
