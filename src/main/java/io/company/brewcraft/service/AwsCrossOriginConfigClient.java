package io.company.brewcraft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketCrossOriginConfigurationRequest;

import io.company.brewcraft.model.IaasBucketCrossOriginConfiguration;

public class AwsCrossOriginConfigClient implements IaasClient<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration>{
    private static final Logger log = LoggerFactory.getLogger(AwsCrossOriginConfigClient.class);

    private final AmazonS3 awsClient;

    public AwsCrossOriginConfigClient(final AmazonS3 awsClient) {
        this.awsClient = awsClient;
    }

    @Override
    public IaasBucketCrossOriginConfiguration get(String bucketName) {
        final GetBucketCrossOriginConfigurationRequest request = new GetBucketCrossOriginConfigurationRequest(bucketName);

        BucketCrossOriginConfiguration bucketCrossOriginConfiguration = null;
        try {
            bucketCrossOriginConfiguration = awsClient.getBucketCrossOriginConfiguration(request);
        } catch (AmazonS3Exception e) {
            log.error("Failed to get the cross origin configuration for bucket: {}", bucketName);
            throw e;
        }

        IaasBucketCrossOriginConfiguration iaasBucketCrossOriginConfiguration = new IaasBucketCrossOriginConfiguration(bucketName, bucketCrossOriginConfiguration);
        return iaasBucketCrossOriginConfiguration;
    }

    @Override
    public <BE extends IaasBucketCrossOriginConfiguration> IaasBucketCrossOriginConfiguration add(BE entity) {
        return this.put(entity);
    }

    @Override
    public <UE extends IaasBucketCrossOriginConfiguration> IaasBucketCrossOriginConfiguration put(UE entity) {
        final SetBucketCrossOriginConfigurationRequest request = new SetBucketCrossOriginConfigurationRequest(entity.getBucketName(), entity.getBucketCrossOriginConfiguration());
        try {
            this.awsClient.setBucketCrossOriginConfiguration(request);
        } catch (AmazonS3Exception e) {
            log.error("Failed to put the cross origin configuration for bucket: {}", entity.getBucketName());
            throw e;
        }

        return this.get(entity.getBucketName());
    }

    @Override
    public boolean exists(String bucketName) {
        IaasBucketCrossOriginConfiguration result = get(bucketName);
        boolean exists = result != null && result.getBucketCrossOriginConfiguration() != null;
        return exists;
    }

    @Override
    public boolean delete(final String bucketName) {
        boolean success = false;

        final DeleteBucketCrossOriginConfigurationRequest request = new DeleteBucketCrossOriginConfigurationRequest(bucketName);
        try {
            this.awsClient.deleteBucketCrossOriginConfiguration(request);
            success = true;
        } catch (AmazonS3Exception e) {
            log.error("Failed to delete the cross origin configuration for bucket: {}", bucketName);
        }

        return success;
    }
}
