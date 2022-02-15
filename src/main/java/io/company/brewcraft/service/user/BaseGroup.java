package io.company.brewcraft.service.user;

import io.company.brewcraft.service.IaasRoleAccessor;

public interface BaseGroup extends IaasRoleAccessor {
    String getName();

    void setName(String name);
}
