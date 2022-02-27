package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasTenantUserMembership;

public interface IdpTenantUserMembershipRepository {
    void add(IaasTenantUserMembership membership);

    void remove(IaasTenantUserMembership membership);
}
