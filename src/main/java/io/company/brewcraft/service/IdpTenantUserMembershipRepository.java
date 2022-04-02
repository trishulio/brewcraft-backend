package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasUserTenantMembership;

public interface IdpTenantUserMembershipRepository {
    void add(IaasUserTenantMembership membership);

    void remove(IaasUserTenantMembership membership);
}
