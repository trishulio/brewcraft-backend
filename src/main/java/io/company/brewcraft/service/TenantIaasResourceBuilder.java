package io.company.brewcraft.service;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasBucketCrossOriginConfiguration;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public interface TenantIaasResourceBuilder {
    <T extends BaseIaasIdpTenant> String getRoleName(T iaasIdpTenant);
    <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasIdpTenant);

    <T extends BaseIaasIdpTenant> String getVfsPolicyName(T iaasIdpTenant);
    <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildVfsPolicy(T iaasIdpTenant);

    <T extends BaseIaasIdpTenant> String getObjectStoreName(T iaasIdpTenant);
    <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasIdpTenant);

    <T extends BaseIaasIdpTenant> IaasRolePolicyAttachmentId buildVfsAttachmentId(T iaasIdpTenant);
    <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasRole role, IaasPolicy policy);

    <T extends BaseIaasIdpTenant> IaasBucketCrossOriginConfiguration buildBucketCrossOriginConfiguration(T iaasIdpTenant);
}
