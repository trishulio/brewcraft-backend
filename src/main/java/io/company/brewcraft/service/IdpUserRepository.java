package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasTenant;
import io.company.brewcraft.model.IaasUser;

public interface IdpUserRepository {
    void createUser(IaasUser user);
    
    void createUserInTenant(IaasUser user, IaasTenant tenant);

    void updateUser(IaasUser user);

    void deleteUser(String username);
}
