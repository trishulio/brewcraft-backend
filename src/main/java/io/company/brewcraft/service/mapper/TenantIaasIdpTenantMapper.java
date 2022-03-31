package io.company.brewcraft.service.mapper;

import java.util.List;
import java.util.UUID;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseTenant;
import io.company.brewcraft.model.IaasIdpTenant;

public class TenantIaasIdpTenantMapper {
    public static final TenantIaasIdpTenantMapper INSTANCE = new TenantIaasIdpTenantMapper();

    protected TenantIaasIdpTenantMapper() {}

    @SuppressWarnings("unchecked")
    public <Idp extends BaseIaasIdpTenant, T extends BaseTenant> List<Idp> fromTenants(List<T> tenants) {
        List<Idp> idpTenants = null;

        if (tenants != null) {
            idpTenants = tenants.stream().map(tenant -> (Idp) fromTenant(tenant)).toList();
        }

        return idpTenants;
    }

    @SuppressWarnings("unchecked")
    public <Idp extends BaseIaasIdpTenant, T extends BaseTenant> Idp fromTenant(T tenant) {
        Idp idpTenant = null;

        if (tenant != null) {
            idpTenant = (Idp) new IaasIdpTenant();

            UUID id = tenant.getId();
            if (id != null) {
                idpTenant.setName(id.toString());
            } else {
                idpTenant.setName(null);
            }
            idpTenant.setIaasRole(null);
        }

        return idpTenant;
    }
}
