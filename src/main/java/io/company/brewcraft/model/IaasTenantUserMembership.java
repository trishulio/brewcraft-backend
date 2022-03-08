package io.company.brewcraft.model;

public class IaasTenantUserMembership extends BaseEntity {
    private IaasUser user;
    private BaseIaasIdpTenant tenant;

    public IaasTenantUserMembership() {
        super();
    }

    public IaasTenantUserMembership(IaasUser user, IaasIdpTenant tenant) {
        this();
        setUser(user);
        setTenant(tenant);
    }

    public IaasUser getUser() {
        return user;
    }

    public void setUser(IaasUser user) {
        this.user = user;
    }

    public BaseIaasIdpTenant getTenant() {
        return tenant;
    }

    public void setTenant(BaseIaasIdpTenant tenant) {
        this.tenant = tenant;
    }
}
