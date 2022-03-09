package io.company.brewcraft.model;

public interface IaasIdpTenantAccessor {
    IaasIdpTenant getIdpTenant();

    void setIdpTenant(IaasIdpTenant idpTenant);
}
