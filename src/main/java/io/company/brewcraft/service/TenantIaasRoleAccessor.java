package io.company.brewcraft.service;

import io.company.brewcraft.model.TenantIaasRole;

public interface TenantIaasRoleAccessor {
    TenantIaasRole getTenantRole();

    void setTenantRole(TenantIaasRole tenantRole);
}
