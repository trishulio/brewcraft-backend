package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasIdpTenantUserMembership;

public interface IdpTenantUserMembershipRepository {
    void add(IaasIdpTenantUserMembership membership);

    void remove(IaasIdpTenantUserMembership membership);
}
