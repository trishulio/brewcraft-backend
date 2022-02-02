package io.company.brewcraft.model;

public interface BaseTenantObjectStore extends TenantAccessor {
    String getName();

    void setName(String url);
}
