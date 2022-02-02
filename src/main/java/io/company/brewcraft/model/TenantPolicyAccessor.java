package io.company.brewcraft.model;

public interface TenantPolicyAccessor {
    TenantPolicy getTenantPolicy();

    void setTenantPolicy(TenantPolicy tenantPolicy);
}
