package io.company.brewcraft.service;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.TenantIdProvider;
import io.company.brewcraft.security.session.ContextHolder;

public class AwsTenantBucketNameProvider implements ObjectStoreNameProvider {
    private final String defaultAppBucketName;
    private AwsDocumentTemplates templates;
    private ContextHolder contextHolder;

    public AwsTenantBucketNameProvider(AwsDocumentTemplates templates, ContextHolder contextHolder, String defaultAppBucketName) {
        this.templates = templates;
        this.contextHolder = contextHolder;
        this.defaultAppBucketName = defaultAppBucketName;
    }

    @Override
    public String getObjectStoreName() {
        String objectStoreName = this.defaultAppBucketName;

        TenantIdProvider tenantIdProvider = this.contextHolder.getPrincipalContext();

        String tenantId = null;
        if (tenantIdProvider != null) {
            tenantId = tenantIdProvider.getTenantId();
        }

        String bucketName = null;
        if (tenantId != null) {
            bucketName = this.templates.getTenantVfsBucketName(tenantId);
        }

        if (bucketName != null) {
            objectStoreName = bucketName;
        }

        return objectStoreName;
    }

}
