package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasRole;

public interface IaasRoleAccessor {
    final String ATTR_IAAS_ROLE = "iaasRole";

    IaasRole getIaasRole();

    void setIaasRole(IaasRole iaasRole);
}
