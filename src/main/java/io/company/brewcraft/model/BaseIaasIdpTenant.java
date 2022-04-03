package io.company.brewcraft.model;

import io.company.brewcraft.service.IaasRoleAccessor;

public interface BaseIaasIdpTenant extends IaasRoleAccessor {
    final String ATTR_NAME = "name";
    final String ATTR_DESCRIPTION = "description";

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
