package io.company.brewcraft.service;

import java.net.URL;
import java.util.Date;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class AwsObjectStoreFileRemoteClient implements AwsObjectStoreFileSystemClient {
    private AmazonS3 s3Client;
    private String bucketName;

    public AwsObjectStoreFileRemoteClient(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }
    
    @Override
    public URL presign(String fileKey, Date expiration, HttpMethod httpMethod) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(this.bucketName, fileKey, httpMethod)
                                              .withExpiration(expiration);
        
        return s3Client.generatePresignedUrl(request);
    }
}
