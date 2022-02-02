package io.company.brewcraft.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;

public class AwsS3Client {
    private AmazonS3 s3Client;

    public AwsS3Client(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public Bucket createBucket(String bucketName) {
        CreateBucketRequest request = new CreateBucketRequest(bucketName);
        Bucket bucket = this.s3Client.createBucket(request);

        return bucket;
    }
}
