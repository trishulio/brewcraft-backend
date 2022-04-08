package io.company.brewcraft.model;

public class TenantIaasIdpDeleteResult extends BaseModel {
    private long idpTenant;

    public TenantIaasIdpDeleteResult(long idpTenant) {
        this.idpTenant = idpTenant;
    }

    public long getIdpTenant() {
        return idpTenant;
    }

    public void setIdpTenant(long idpTenant) {
        this.idpTenant = idpTenant;
    }
}
