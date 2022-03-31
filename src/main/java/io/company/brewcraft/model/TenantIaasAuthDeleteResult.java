package io.company.brewcraft.model;

public class TenantIaasAuthDeleteResult extends BaseModel {
    private long roles;

    public TenantIaasAuthDeleteResult(long roles) {
        setRoles(roles);
    }

    public long getRoles() {
        return roles;
    }

    public void setRoles(long roles) {
        this.roles = roles;
    }
}
