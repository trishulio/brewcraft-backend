package io.company.brewcraft.model;

public class TenantIaasIdpResources extends BaseModel {
    private IaasIdpTenant iaasTenant;

    public TenantIaasIdpResources() {
        super();
    }

    public TenantIaasIdpResources(IaasIdpTenant iaasTenant) {
        this();
        setIaasTenant(iaasTenant);
    }

    public BaseIaasIdpTenant getIaasTenant() {
        return iaasTenant;
    }

    public void setIaasTenant(IaasIdpTenant iaasTenant) {
        this.iaasTenant = iaasTenant;
    }

}
