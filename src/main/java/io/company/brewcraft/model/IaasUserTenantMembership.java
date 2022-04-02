package io.company.brewcraft.model;

import io.company.brewcraft.service.CrudEntity;

public class IaasUserTenantMembership extends BaseEntity implements CrudEntity<IaasUserTenantMembershipId>,  UpdateIaasUserTenantMembership {
    private IaasUser user;
    private IaasIdpTenant tenant;

    public IaasUserTenantMembership() {
        super();
    }

    public IaasUserTenantMembership(IaasUser user, IaasIdpTenant idpTenant) {
        this();
        setUser(user);
        setTenant(idpTenant);
    }

    @Override
    public IaasUserTenantMembershipId getId() {
        return IaasUserTenantMembershipId.build(this.user, this.tenant);
    }

    @Override
    public void setId(IaasUserTenantMembershipId id) {
        if (id != null) {
            if (user == null) {
                user = new IaasUser(id.getUserId());
            }
            if (tenant == null) {
                tenant = new IaasIdpTenant(id.getTenantId());
            }
        }
    }

    @Override
    public IaasUser getUser() {
        return user;
    }

    @Override
    public void setUser(IaasUser user) {
        this.user = user;
    }

    @Override
    public IaasIdpTenant getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(IaasIdpTenant idpTenant) {
        this.tenant = idpTenant;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }
}
