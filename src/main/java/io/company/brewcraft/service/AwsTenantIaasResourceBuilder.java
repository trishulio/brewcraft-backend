package io.company.brewcraft.service;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;

public class AwsTenantIaasResourceBuilder implements TenantIaasResourceBuilder {
    private AwsDocumentTemplates templates;

    public AwsTenantIaasResourceBuilder(AwsDocumentTemplates templates) {
        this.templates = templates;
    }

    @Override
    public <T extends BaseIaasIdpTenant> String getRoleName(T iaasTenant) {
        String iaasTenantId = iaasTenant.getName();
        return this.templates.getTenantIaasRoleName(iaasTenantId);
    }

    @Override
    public <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasTenant) {
        String iaasTenantId = iaasTenant.getName();
        @SuppressWarnings("unchecked")
        R role = (R) new IaasRole();
        role.setName(this.templates.getTenantIaasRoleName(iaasTenantId));
        role.setDescription(this.templates.getTenantIaasRoleDescription(iaasTenantId));
        role.setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());

        return role;
    }

    @Override
    public <T extends BaseIaasIdpTenant> String getPolicyName(T iaasTenant) {
        String iaasTenantId = iaasTenant.getName();

        return this.templates.getTenantVfsPolicyName(iaasTenantId);
    }

    @Override
    public <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildPolicy(T iaasTenant) {
        String iaasTenantId = iaasTenant.getName();
        @SuppressWarnings("unchecked")
        P policy = (P) new IaasPolicy();
        policy.setName(this.templates.getTenantVfsPolicyName(iaasTenantId));
        policy.setDescription(this.templates.getTenantVfsPolicyDescription(iaasTenantId));
        policy.setDocument(this.templates.getTenantBucketPolicyDoc(iaasTenantId));

        return policy;
    }

    @Override
    public <T extends BaseIaasIdpTenant> String getObjectStoreName(T iaasTenant) {
        String iaasTenantId = iaasTenant.getName();

        return this.templates.getTenantVfsBucketName(iaasTenantId);
    }

    @Override
    public <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasTenant) {
        String iaasTenantId = iaasTenant.getName();
        @SuppressWarnings("unchecked")
        O objectStore = (O) new IaasObjectStore();
        objectStore.setName(this.templates.getTenantVfsBucketName(iaasTenantId));

        return objectStore;
    }

    @Override
    public <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasPolicy policy, IaasRole role) {
        @SuppressWarnings("unchecked")
        A attachment = (A) new IaasRolePolicyAttachment();
        
        attachment.setIaasPolicy(policy);
        attachment.setIaasRole(role);
        
        return attachment;
    }
}
