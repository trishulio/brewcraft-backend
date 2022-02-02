package io.company.brewcraft.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import io.company.brewcraft.model.TenantObjectStore;

public class TenantObjectStoreIaasRepository {

    private AwsS3Client s3Client;
    
    public TenantObjectStoreIaasRepository(AwsS3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void putObjectStores(List<TenantObjectStore> objectStores) {
        // TODO: Replace create method with put for idempotency
        CompletableFuture<?>[] operations = objectStores.stream()
                                                        .filter(objectStore -> objectStore != null)
                                                        .map(objectStore -> objectStore.getName())
                                                        .filter(objectStoreName -> objectStoreName != null)
                                                        .map(objectStoreName -> CompletableFuture.supplyAsync(() -> s3Client.createBucket(objectStoreName)))
                                                        .collect(Collectors.toList())
                                                        .toArray(new CompletableFuture<?>[0]);

        try {
            CompletableFuture.allOf(operations).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(String.format("Failed to put object-stores because: %s", e.getMessage()), e);
        }
    }    
}
