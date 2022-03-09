package io.company.brewcraft.model;

public class IaasIdpTenantUserMembership extends BaseEntity {
    private IaasUser user;
    private BaseIaasIdpTenant idpTenant;

    public IaasIdpTenantUserMembership() {
        super();
    }

    public IaasIdpTenantUserMembership(IaasUser user, IaasIdpTenant idpTenant) {
        this();
        setUser(user);
        setTenant(idpTenant);
    }

    public IaasUser getUser() {
        return user;
    }

    public void setUser(IaasUser user) {
        this.user = user;
    }

    public BaseIaasIdpTenant getTenant() {
        return idpTenant;
    }

    public void setTenant(BaseIaasIdpTenant idpTenant) {
        this.idpTenant = idpTenant;
    }
}
