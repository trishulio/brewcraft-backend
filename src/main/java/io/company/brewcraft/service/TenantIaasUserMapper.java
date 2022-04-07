package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.BaseIaasUser;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.model.user.BaseUser;

public class TenantIaasUserMapper {
    public static final TenantIaasUserMapper INSTANCE = new TenantIaasUserMapper();

    protected TenantIaasUserMapper() {}

    @SuppressWarnings("unchecked")
    public <IdpUser extends BaseIaasUser, BU extends BaseUser> List<IdpUser> fromUsers(List<BU> users) {
        List<IdpUser> idpUsers = null;

        if (users != null) {
            idpUsers = users.stream().map(user -> (IdpUser) fromUser(user)).toList();
        }

        return idpUsers;
    }

    @SuppressWarnings("unchecked")
    public <IdpUser extends BaseIaasUser, BU extends BaseUser> IdpUser fromUser(BU user) {
        IdpUser idpUser = null;

        if (user != null) {
            idpUser = (IdpUser) new IaasUser(user.getUserName(), user.getEmail(), user.getPhoneNumber(), null, null);
        }

        return idpUser;
    }
}
