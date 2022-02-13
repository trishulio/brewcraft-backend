package io.company.brewcraft.model;

import com.amazonaws.services.s3.AmazonS3;

public interface AmazonS3Provider {
    AmazonS3 getAmazonS3Client();
}
