package io.company.brewcraft.service;

import java.net.URL;
import java.util.Date;

import com.amazonaws.HttpMethod;

public interface AwsObjectStoreFileSystemClient {
    URL presign(String fileKey, Date expiration, HttpMethod httpMethod);
}
