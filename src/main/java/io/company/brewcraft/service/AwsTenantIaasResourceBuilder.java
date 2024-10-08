package io.company.brewcraft.service;

import java.util.List;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasObjectStoreAccessConfig;
import io.company.brewcraft.model.IaasObjectStoreCorsConfiguration;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public class AwsTenantIaasResourceBuilder implements TenantIaasResourceBuilder {
    private AwsDocumentTemplates templates;

    private List<String> allowedHeaders;
    private List<String> allowedMethods;
    private List<String> allowedOrigins;
    private boolean blockPublicAcls;
    private boolean ignorePublicAcls;
    private boolean blockPublicPolicy;
    private boolean restrictPublicBuckets;

    public AwsTenantIaasResourceBuilder(AwsDocumentTemplates templates, List<String> allowedHeaders, List<String> allowedMethods, List<String> allowedOrigins, boolean blockPublicAcls, boolean ignorePublicAcls, boolean blockPublicPolicy, boolean restrictPublicBuckets) {
        this.templates = templates;
        this.allowedHeaders = allowedHeaders;
        this.allowedMethods = allowedMethods;
        this.allowedOrigins = allowedOrigins;
        this.blockPublicAcls = blockPublicAcls;
        this.ignorePublicAcls = ignorePublicAcls;
        this.blockPublicPolicy = blockPublicPolicy;
        this.restrictPublicBuckets = restrictPublicBuckets;
    }

    @Override
    public String getRoleId(String iaasIdpTenantId) {
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
    public String getVfsPolicyId(String iaasIdpTenantId) {
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
    public String getObjectStoreId(String iaasIdpTenantId) {
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
    public IaasRolePolicyAttachmentId buildVfsAttachmentId(String iaasIdpTenantId) {
        IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId();
        id.setPolicyId(this.templates.getTenantVfsPolicyName(iaasIdpTenantId));
        id.setRoleId(this.templates.getTenantIaasRoleName(iaasIdpTenantId));

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

    @Override
    public <T extends BaseIaasIdpTenant> IaasObjectStoreCorsConfiguration buildObjectStoreCorsConfiguration(T iaasIdpTenant) {
        List<String> sanitizedOrigins = allowedOrigins.stream().map(o -> o.replaceAll("/*$", "")).toList();

        CORSRule corsRule = new CORSRule().withAllowedHeaders(allowedHeaders)
                                          .withAllowedMethods(allowedMethods.stream().map(method -> AllowedMethods.valueOf(method)).toList())
                                          .withAllowedOrigins(sanitizedOrigins);

        List<CORSRule> corsRules = List.of(corsRule);

        String bucketName = this.getObjectStoreId(iaasIdpTenant.getName());

        return new IaasObjectStoreCorsConfiguration(bucketName, new BucketCrossOriginConfiguration(corsRules));
    }

    @Override
    public <T extends BaseIaasIdpTenant> IaasObjectStoreAccessConfig buildPublicAccessBlock(T iaasIdpTenant) {
        PublicAccessBlockConfiguration publicAccessBlockConfiguration = new PublicAccessBlockConfiguration().withBlockPublicAcls(blockPublicAcls)
                                                                                                            .withBlockPublicPolicy(blockPublicPolicy)
                                                                                                            .withIgnorePublicAcls(ignorePublicAcls)
                                                                                                            .withRestrictPublicBuckets(restrictPublicBuckets);

        String bucketName = this.getObjectStoreId(iaasIdpTenant.getName());
        return new IaasObjectStoreAccessConfig(bucketName, publicAccessBlockConfiguration);
    }
}
