package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasRole;

public interface IaasRoleAccessor {
    IaasRole getIaasRole();

    void setIaasRole(IaasRole tenantRole);
}
