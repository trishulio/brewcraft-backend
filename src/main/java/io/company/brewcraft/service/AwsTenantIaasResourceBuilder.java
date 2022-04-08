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
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public class AwsTenantIaasResourceBuilder implements TenantIaasResourceBuilder {
    private AwsDocumentTemplates templates;

    public AwsTenantIaasResourceBuilder(AwsDocumentTemplates templates) {
        this.templates = templates;
    }

    @Override
    public <T extends BaseIaasIdpTenant> String getRoleName(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        return this.templates.getTenantIaasRoleName(iaasIdpTenantId);
    }

    @Override
    public <R extends BaseIaasRole, T extends BaseIaasIdpTenant> R buildRole(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        @SuppressWarnings("unchecked")
        R role = (R) new IaasRole();
        role.setName(this.templates.getTenantIaasRoleName(iaasIdpTenantId));
        role.setDescription(this.templates.getTenantIaasRoleDescription(iaasIdpTenantId));
        role.setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());

        return role;
    }

    @Override
    public <T extends BaseIaasIdpTenant> String getVfsPolicyName(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();

        return this.templates.getTenantVfsPolicyName(iaasIdpTenantId);
    }

    @Override
    public <P extends BaseIaasPolicy, T extends BaseIaasIdpTenant> P buildVfsPolicy(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        @SuppressWarnings("unchecked")
        P policy = (P) new IaasPolicy();
        policy.setName(this.templates.getTenantVfsPolicyName(iaasIdpTenantId));
        policy.setDescription(this.templates.getTenantVfsPolicyDescription(iaasIdpTenantId));
        policy.setDocument(this.templates.getTenantBucketPolicyDoc(iaasIdpTenantId));

        return policy;
    }

    @Override
    public <T extends BaseIaasIdpTenant> String getObjectStoreName(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();

        return this.templates.getTenantVfsBucketName(iaasIdpTenantId);
    }

    @Override
    public <O extends BaseIaasObjectStore, T extends BaseIaasIdpTenant> O buildObjectStore(T iaasIdpTenant) {
        String iaasIdpTenantId = iaasIdpTenant.getName();
        @SuppressWarnings("unchecked")
        O objectStore = (O) new IaasObjectStore();
        objectStore.setName(this.templates.getTenantVfsBucketName(iaasIdpTenantId));

        return objectStore;
    }

    @Override
    public <T extends BaseIaasIdpTenant> IaasRolePolicyAttachmentId buildVfsAttachmentId(T iaasIdpTenant) {
        IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId();
        id.setPolicyId(this.templates.getTenantVfsPolicyName(iaasIdpTenant.getName()));
        id.setRoleId(this.templates.getTenantIaasRoleName(iaasIdpTenant.getName()));

        return id;
    }

    @Override
    public <A extends BaseIaasRolePolicyAttachment> A buildAttachment(IaasRole role, IaasPolicy policy) {
        @SuppressWarnings("unchecked")
        A attachment = (A) new IaasRolePolicyAttachment();

        attachment.setIaasRole(role);
        attachment.setIaasPolicy(policy);

        return attachment;
    }
}
