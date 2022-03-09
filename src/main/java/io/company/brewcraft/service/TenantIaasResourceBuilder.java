package io.company.brewcraft.service;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;

public interface TenantIaasResourceBuilder {

    <T extends BaseIaasIdpTenant> String getRoleName(T iaasIdpTenant);
    <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasIdpTenant);

    <T extends BaseIaasIdpTenant> String getPolicyName(T iaasIdpTenant);
    <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildPolicy(T iaasIdpTenant);

    <T extends BaseIaasIdpTenant> String getObjectStoreName(T iaasIdpTenant);
    <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasIdpTenant);

    <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasPolicy policy, IaasRole role);
}
