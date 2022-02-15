package io.company.brewcraft.model;

import io.company.brewcraft.service.AwsObjectStoreFileSystemClient;

public interface AwsObjectStoreFileClientProvider {
    AwsObjectStoreFileSystemClient getClient();
}
