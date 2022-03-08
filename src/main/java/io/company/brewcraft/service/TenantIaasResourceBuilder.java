package io.company.brewcraft.service;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;

public interface TenantIaasResourceBuilder {
    
    <T extends BaseIaasIdpTenant> String getRoleName(T iaasTenant);    
    <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasTenant);

    <T extends BaseIaasIdpTenant> String getPolicyName(T iaasTenant);
    <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildPolicy(T iaasTenant);

    <T extends BaseIaasIdpTenant> String getObjectStoreName(T iaasTenant);
    <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasTenant);
    
    <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasPolicy policy, IaasRole role);
}
