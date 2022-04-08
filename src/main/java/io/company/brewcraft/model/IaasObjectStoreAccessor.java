package io.company.brewcraft.model;

public interface IaasObjectStoreAccessor {
    IaasObjectStore getIaasObjectStore();

    void setIaasObjectStore(IaasObjectStore tenantObjectStore);
}
