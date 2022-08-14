package io.company.brewcraft.service;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasObjectStoreAccessConfig;
import io.company.brewcraft.model.IaasObjectStoreCorsConfiguration;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public interface TenantIaasResourceBuilder {
    String getRoleId(String iaasIdpTenantId);
    <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasIdpTenant);

    String getVfsPolicyId(String iaasIdpTenantId);
    <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildVfsPolicy(T iaasIdpTenant);

    String getObjectStoreId(String iaasIdpTenantId);
    <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasIdpTenant);

    IaasRolePolicyAttachmentId buildVfsAttachmentId(String iaasIdpTenantId);
    <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasRole role, IaasPolicy policy);

    <T extends BaseIaasIdpTenant> IaasObjectStoreCorsConfiguration buildObjectStoreCorsConfiguration(T iaasIdpTenant);
    <T extends BaseIaasIdpTenant> IaasObjectStoreAccessConfig buildPublicAccessBlock(T iaasIdpTenant);
}
