package io.company.brewcraft.model;

public class TenantIaasIdpResources extends BaseModel {
    private IaasIdpTenant idpTenant;

    public TenantIaasIdpResources() {
        super();
    }

    public TenantIaasIdpResources(IaasIdpTenant idpTenant) {
        this();
        setIaasIdpTenant(idpTenant);
    }

    public BaseIaasIdpTenant getIaasIdpTenant() {
        return idpTenant;
    }

    public void setIaasIdpTenant(IaasIdpTenant idpTenant) {
        this.idpTenant = idpTenant;
    }
}
