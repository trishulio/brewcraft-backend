package io.company.brewcraft.model.user;

interface BaseUserRole<RT extends BaseUserRoleType, U extends BaseUser<? extends BaseUserRole<RT, U>>> extends UserRoleTypeAccessor, UserAccessor {
}
