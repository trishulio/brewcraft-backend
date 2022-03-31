package io.company.brewcraft.model;

import java.net.URI;

import io.company.brewcraft.service.IaasClient;

public interface IaasObjectStoreFileClientProvider {
    IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> getClient();
}
