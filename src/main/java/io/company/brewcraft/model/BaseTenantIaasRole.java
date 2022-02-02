package io.company.brewcraft.model;

import java.util.List;

public interface BaseTenantIaasRole extends TenantAccessor {
    String getRoleName();

    void setRoleName(String roleName);
    
    List<TenantPolicy> getPolicies();
    
    void setPolicies(List<TenantPolicy> policies);
}
