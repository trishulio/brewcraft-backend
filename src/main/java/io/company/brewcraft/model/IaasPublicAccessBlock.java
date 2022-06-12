package io.company.brewcraft.model;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

import io.company.brewcraft.service.CrudEntity;

public class IaasPublicAccessBlock extends BaseEntity implements CrudEntity<String> {

    private String bucketName;

    private PublicAccessBlockConfiguration publicAccessBlockConfig;

    public IaasPublicAccessBlock() {
        super();
    }

    public IaasPublicAccessBlock(String id) {
        this();
        setId(id);
    }

    public IaasPublicAccessBlock(String bucketName,
            PublicAccessBlockConfiguration publicAccessBlockConfig) {
        this();
        this.bucketName = bucketName;
        this.publicAccessBlockConfig = publicAccessBlockConfig;
    }

    @Override
    public void setId(String id) {
        setBucketName(id);
    }

    @Override
    public String getId() {
        return getBucketName();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public PublicAccessBlockConfiguration getPublicAccessBlockConfig() {
        return publicAccessBlockConfig;
    }

    public void setPublicAccessBlockConfig(PublicAccessBlockConfiguration publicAccessBlockConfig) {
        this.publicAccessBlockConfig = publicAccessBlockConfig;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }

}
