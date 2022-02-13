package io.company.brewcraft.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;

public class AwsObjectStoreClient {
    private AmazonS3 awsClient;

    public AwsObjectStoreClient(AmazonS3 awsClient) {
        this.awsClient = awsClient;
    }
    
    public Bucket get(String bucketName) {
        throw new UnsupportedOperationException("AWS SDK 1 doesn't have a GetBucketRequest operations. Only specific bucket attributes can be fetched. This method is unsupported until JDK is upgraded to v2");
    }
    
    public void delete(String bucketName) {
        DeleteBucketRequest request = new DeleteBucketRequest(bucketName);
        
        this.awsClient.deleteBucket(request);
    }
    
    public Bucket add(String bucketName) {
        CreateBucketRequest request = new CreateBucketRequest(bucketName);
        // TODO: Implement bucket policy to be restricted.
        
        return this.awsClient.createBucket(request);
    }
    
    public Bucket put(String bucketName) {
        throw new UnsupportedOperationException("Not implemented due to lack of use-case.");        
    }
}
