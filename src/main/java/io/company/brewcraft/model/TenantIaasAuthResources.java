package io.company.brewcraft.model;

public class TenantIaasAuthResources extends BaseModel {
    private IaasRole role;

    public TenantIaasAuthResources() {
        super();
    }

    public TenantIaasAuthResources(IaasRole role) {
        this();
        setRole(role);
    }

    public IaasRole getRole() {
        return role;
    }

    public void setRole(IaasRole role) {
        this.role = role;
    }
}
