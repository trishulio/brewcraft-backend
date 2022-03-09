package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasUser;

public interface IdpUserRepository {
    void createUser(IaasUser user);

    void createUserInTenant(IaasUser user, IaasIdpTenant idpTenant);

    void updateUser(IaasUser user);

    void deleteUser(String username);
}
