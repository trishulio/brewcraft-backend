package io.company.brewcraft.model;

public interface IaasPolicyAccessor {
    IaasPolicy getIaasPolicy();

    void setIaasPolicy(IaasPolicy tenantPolicy);
}
