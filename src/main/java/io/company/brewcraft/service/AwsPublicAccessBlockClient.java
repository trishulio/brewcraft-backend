package io.company.brewcraft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeletePublicAccessBlockRequest;
import com.amazonaws.services.s3.model.GetPublicAccessBlockRequest;
import com.amazonaws.services.s3.model.GetPublicAccessBlockResult;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;

import io.company.brewcraft.model.IaasPublicAccessBlock;

public class AwsPublicAccessBlockClient implements IaasClient<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> {
    private static final Logger log = LoggerFactory.getLogger(AwsPublicAccessBlockClient.class);

    private final AmazonS3 awsClient;

    public AwsPublicAccessBlockClient(final AmazonS3 awsClient) {
        this.awsClient = awsClient;
    }

    @Override
    public IaasPublicAccessBlock get(String bucketName) {
        final GetPublicAccessBlockRequest request = new GetPublicAccessBlockRequest().withBucketName(bucketName);

        GetPublicAccessBlockResult publicAccessBlockResult = null;
        try {
            publicAccessBlockResult = awsClient.getPublicAccessBlock(request);
        } catch (AmazonS3Exception e) {
            log.error("Failed to get the public access block configuration for bucket: {}", bucketName);
            throw e;
        }

        IaasPublicAccessBlock iaasPublicAccessBlock = publicAccessBlockResult == null ? null : new IaasPublicAccessBlock(bucketName, publicAccessBlockResult.getPublicAccessBlockConfiguration());
        return iaasPublicAccessBlock;
    }

    @Override
    public <BE extends IaasPublicAccessBlock> IaasPublicAccessBlock add(BE entity) {
        log.debug("Replacing existing public access block configuration on the bucket: {} (if any) since only single public access block configuration can be applied.", entity.getBucketName());
        return this.put(entity);
    }

    @Override
    public <UE extends IaasPublicAccessBlock> IaasPublicAccessBlock put(UE entity) {
        final SetPublicAccessBlockRequest request = new SetPublicAccessBlockRequest().withBucketName(entity.getBucketName())
                                                                                     .withPublicAccessBlockConfiguration(entity.getPublicAccessBlockConfig());
        try {
            this.awsClient.setPublicAccessBlock(request);
        } catch (AmazonS3Exception e) {
            log.error("Failed to put the public access block configuration for bucket: {}", entity.getBucketName());
            throw e;
        }

        return this.get(entity.getBucketName());
    }

    @Override
    public boolean exists(String bucketName) {
        return get(bucketName) != null;
    }

    @Override
    public boolean delete(final String bucketName) {
        boolean success = false;

        final DeletePublicAccessBlockRequest request = new DeletePublicAccessBlockRequest().withBucketName(bucketName);
        try {
            this.awsClient.deletePublicAccessBlock(request);
            success = true;
        } catch (AmazonS3Exception e) {
            log.error("Failed to delete the public access block configuration for bucket: {}", bucketName);
        }

        return success;
    }
}
