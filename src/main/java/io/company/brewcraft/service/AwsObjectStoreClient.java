package io.company.brewcraft.service;

import java.util.List;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.ListBucketsRequest;

public class AwsObjectStoreClient {
    private AmazonS3 awsClient;

    public AwsObjectStoreClient(AmazonS3 awsClient) {
        this.awsClient = awsClient;
    }

    public List<Bucket> getAll() {
        ListBucketsRequest request = new ListBucketsRequest();
        return awsClient.listBuckets(request);    
    }
    
    public Bucket get(String bucketName) {
        List<Bucket> buckets = getAll();
        Optional<Bucket> opt = buckets.stream().filter(bucket -> bucket.getName().equals(bucketName)).findAny();
        
        return opt.orElseThrow(() -> new AmazonS3Exception(String.format("Could not find the bucket with name: %s", bucketName)));
    }

    public void delete(String bucketName) {
        DeleteBucketRequest request = new DeleteBucketRequest(bucketName);

        this.awsClient.deleteBucket(request);
    }

    public Bucket add(String bucketName) {
        CreateBucketRequest request = new CreateBucketRequest(bucketName);

        return this.awsClient.createBucket(request);
    }

    public Bucket put(String bucketName) {
        if (exists(bucketName)) {
            return get(bucketName);
        } else {
            return add(bucketName);
        }
    }
    
    public boolean exists(String bucketName) {
        return awsClient.doesBucketExistV2(bucketName);
    }
}
