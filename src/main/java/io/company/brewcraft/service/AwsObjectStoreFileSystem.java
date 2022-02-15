package io.company.brewcraft.service;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.amazonaws.HttpMethod;

import io.company.brewcraft.model.AwsObjectStoreFileClientProvider;

public class AwsObjectStoreFileSystem implements IaasObjectStoreFileSystem {
    private AwsObjectStoreFileClientProvider clientProvider;
    private BlockingAsyncExecutor executor;
    private LocalDateTimeMapper dtMapper;


    public AwsObjectStoreFileSystem(AwsObjectStoreFileClientProvider clientProvider, BlockingAsyncExecutor executor, LocalDateTimeMapper dtMapper) {
        this.clientProvider = clientProvider;
        this.executor = executor;
        this.dtMapper = dtMapper;
    }

    @Override
    public List<URL> getTemporaryPublicFilePath(List<URI> filePaths, LocalDateTime expiration) {
        AwsObjectStoreFileSystemClient filesystemClient = clientProvider.getClient();

        Date expiry = this.dtMapper.toUtilDate(expiration);
        
        List<Supplier<URL>> suppliers = filePaths.stream()
                .filter(Objects::nonNull)
                .map(URI::toString)
                .map(filePath -> (Supplier<URL>) () -> filesystemClient.presign(filePath, expiry, HttpMethod.GET))
                .toList();
        
        return this.executor.supply(suppliers);
    }
}
