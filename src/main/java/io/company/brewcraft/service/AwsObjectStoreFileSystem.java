package io.company.brewcraft.service;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.amazonaws.HttpMethod;

public class AwsObjectStoreFileSystem implements IaasObjectStoreFileSystem {
    private AwsObjectStoreFileSystemClient filesystemClient;
    private BlockingAsyncExecutor executor;
    private LocalDateTimeMapper dtMapper;
    private ObjectStoreNameProvider objectStoreNameProvider;


    public AwsObjectStoreFileSystem(AwsObjectStoreFileSystemClient filesystemClient, BlockingAsyncExecutor executor, LocalDateTimeMapper dtMapper, ObjectStoreNameProvider objectStoreNameProvider) {
        this.filesystemClient = filesystemClient;
        this.executor = executor;
        this.dtMapper = dtMapper;
        this.objectStoreNameProvider = objectStoreNameProvider;
    }

    @Override
    public List<URL> getTemporaryPublicFilePath(List<URI> filePaths, LocalDateTime expiration) {
        String bucketName = this.objectStoreNameProvider.getObjectStoreName();
        Date expiry = this.dtMapper.toUtilDate(expiration);

        List<Supplier<URL>> suppliers = filePaths.stream()
                .filter(Objects::nonNull)
                .map(URI::toString)
                .map(filePath -> (Supplier<URL>) () -> filesystemClient.presign(bucketName, filePath, expiry, HttpMethod.GET))
                .toList();
        
        return this.executor.supply(suppliers);
    }
}
