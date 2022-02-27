package io.company.brewcraft.model;

public class IaasTenantUserMembership extends BaseEntity {
    private IaasUser user;
    private IaasTenant tenant;

    public IaasTenantUserMembership() {
        super();
    }

    public IaasTenantUserMembership(IaasUser user, IaasTenant tenant) {
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

    public IaasTenant getTenant() {
        return tenant;
    }

    public void setTenant(IaasTenant tenant) {
        this.tenant = tenant;
    }
}
