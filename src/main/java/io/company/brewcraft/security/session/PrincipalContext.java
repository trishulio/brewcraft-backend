package io.company.brewcraft.security.session;

import java.util.List;

import io.company.brewcraft.model.IaasAuthorizationCredentials;
import io.company.brewcraft.model.TenantIdProvider;

public interface PrincipalContext extends TenantIdProvider {
    String getUsername();

    List<String> getRoles();
    
    IaasAuthorizationCredentials getIaasToken();
}
