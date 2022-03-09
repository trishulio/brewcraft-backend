package io.company.brewcraft.service.impl;

import io.company.brewcraft.model.IaasIdpTenantUserMembership;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.service.IdpTenantUserMembershipRepository;

public class AwsIdpTenantUserMembershipRepository implements IdpTenantUserMembershipRepository {
    private IdentityProviderClient idpClient;

    public AwsIdpTenantUserMembershipRepository(IdentityProviderClient idpClient) {
        this.idpClient = idpClient;
    }

    @Override
    public void add(IaasIdpTenantUserMembership membership) {
        this.idpClient.addUserToGroup(membership.getUser().getEmail(), membership.getTenant().getName());
    }

    @Override
    public void remove(IaasIdpTenantUserMembership membership) {
        this.idpClient.removeUserFromGroup(membership.getUser().getEmail(), membership.getTenant().getName());
    }
}
