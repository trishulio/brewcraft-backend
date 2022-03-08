package io.company.brewcraft.model;

import io.company.brewcraft.service.IaasRoleAccessor;

public interface BaseIaasIdpTenant extends IaasRoleAccessor {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
