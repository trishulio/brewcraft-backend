package io.company.brewcraft.service;

import java.net.URL;
import java.util.Date;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import io.company.brewcraft.model.AmazonS3Provider;

public class AwsObjectStoreFileRemoteClient implements AwsObjectStoreFileSystemClient {
    private AmazonS3Provider remoteClientProvider;

    public AwsObjectStoreFileRemoteClient(AmazonS3Provider remoteClientProvider) {
        this.remoteClientProvider = remoteClientProvider;
    }
    
    @Override
    public URL presign(String bucketName, String fileKey, Date expiration, HttpMethod httpMethod) {
        AmazonS3 s3Client = this.remoteClientProvider.getAmazonS3Client();

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileKey, httpMethod)
                                              .withExpiration(expiration);

        return s3Client.generatePresignedUrl(request);
    }
}
