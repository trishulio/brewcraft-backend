package io.company.brewcraft.model;

public interface BaseIaasUserTenantMembership {
    final String ATTR_USER = "user";
    final String ATTR_TENANT = "tenant";

    IaasUser getUser();

    void setUser(IaasUser user);

    IaasIdpTenant getTenant();

    void setTenant(IaasIdpTenant idpTenant);
}
