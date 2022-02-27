package io.company.brewcraft.service.impl.user;

import java.util.HashMap;
import java.util.Map;

import io.company.brewcraft.model.IaasTenant;
import io.company.brewcraft.model.IaasTenantUserMembership;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.service.IdpTenantUserMembershipRepository;
import io.company.brewcraft.service.IdpUserRepository;

public class AwsIdpUserRepository implements IdpUserRepository {
    private IdentityProviderClient idpClient;
    private IdpTenantUserMembershipRepository tenantUserRepo;

    public AwsIdpUserRepository(IdentityProviderClient idpClient, IdpTenantUserMembershipRepository tenantUserRepo) {
        this.idpClient = idpClient;
        this.tenantUserRepo = tenantUserRepo;
    }

    @Override
    public void createUser(IaasUser user) {
        Map<String, String> userAttrs = new HashMap<>();
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL, user.getEmail());
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED, "true"); // TODO: Do we need to change this?

        this.idpClient.createUser(user.getEmail(), userAttrs);
    }
    
    @Override
    public void createUserInTenant(IaasUser user, IaasTenant tenant) {
        this.createUser(user);
        
        IaasTenantUserMembership membership = new IaasTenantUserMembership(user, tenant);

        try {
            this.tenantUserRepo.add(membership);
        } catch (Exception e) {
            this.deleteUser(user.getEmail());
            throw e;
        }
    }

    @Override
    public void updateUser(IaasUser user) {
        Map<String, String> userAttrs = new HashMap<>();
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL, user.getEmail());

        this.idpClient.updateUser(user.getEmail(), userAttrs);
    }
    
    @Override
    public void deleteUser(String username) {
        this.idpClient.deleteUser(username);
    }
}
