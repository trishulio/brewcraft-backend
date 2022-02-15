package io.company.brewcraft.model;

public class TenantIaasVfsResources {
    private IaasObjectStore objectStore;
    private IaasRole role;
    private IaasPolicy policy;
    private IaasRolePolicyAttachment rolePolicyAttachment;

    public TenantIaasVfsResources() {
    }

    public TenantIaasVfsResources(IaasObjectStore objectStore, IaasPolicy policy, IaasRole role, IaasRolePolicyAttachment rolePolicyAttachment) {
        this();
        setObjectStore(objectStore);
        setRole(role);
        setPolicy(policy);
        setRolePolicyAttachment(rolePolicyAttachment);
    }

    public IaasObjectStore getObjectStore() {
        return objectStore;
    }

    public void setObjectStore(IaasObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public IaasRole getRole() {
        return role;
    }

    public void setRole(IaasRole role) {
        this.role = role;
    }

    public IaasPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(IaasPolicy policy) {
        this.policy = policy;
    }

    public IaasRolePolicyAttachment getRolePolicyAttachment() {
        return rolePolicyAttachment;
    }

    public void setRolePolicyAttachment(IaasRolePolicyAttachment rolePolicyAttachment) {
        this.rolePolicyAttachment = rolePolicyAttachment;
    }
}
