package io.company.brewcraft.model;

public class TenantIaasVfsResources {
    private IaasObjectStore objectStore;
    private IaasPolicy policy;

    public TenantIaasVfsResources() {
    }

    public TenantIaasVfsResources(IaasObjectStore objectStore, IaasPolicy policy) {
        this();
        setObjectStore(objectStore);
        setPolicy(policy);
    }

    public IaasObjectStore getObjectStore() {
        return objectStore;
    }

    public void setObjectStore(IaasObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public IaasPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(IaasPolicy policy) {
        this.policy = policy;
    }
}
