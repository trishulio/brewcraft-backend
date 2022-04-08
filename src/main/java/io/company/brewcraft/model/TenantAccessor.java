package io.company.brewcraft.model;

public interface TenantAccessor {
    Tenant getTenant();

    void setTenant(Tenant tenant);
}
