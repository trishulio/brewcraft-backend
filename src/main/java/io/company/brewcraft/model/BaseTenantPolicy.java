package io.company.brewcraft.model;

public interface BaseTenantPolicy extends TenantAccessor {
    String getIaasResourceName();

    void setIaasResourceName(String iaasResourceName);
}
