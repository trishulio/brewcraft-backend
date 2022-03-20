package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.amazonaws.services.s3.model.Bucket;

import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.UpdateIaasObjectStore;

public class IaasObjectStoreIaasRepository {
    private AwsObjectStoreClient iamClient;
    private BlockingAsyncExecutor executor;
    private AwsIaasObjectStoreMapper mapper;

    public IaasObjectStoreIaasRepository(AwsObjectStoreClient iamClient, BlockingAsyncExecutor executor, AwsIaasObjectStoreMapper mapper) {
        this.iamClient = iamClient;
        this.executor = executor;
        this.mapper = mapper;
    }

    public List<IaasObjectStore> get(Collection<String> ids) {
        List<Bucket> buckets = iamClient.getAll()
                                        .stream()
                                        .filter(bucket -> ids.contains(bucket.getName()))
                                        .toList();

        return this.mapper.fromObjectStores(buckets);
    }

    public List<IaasObjectStore> add(Collection<? extends BaseIaasObjectStore> objectStores) {
        List<Supplier<Bucket>> suppliers = objectStores.stream()
                .filter(Objects::nonNull)
                .map(bucket -> (Supplier<Bucket>) () -> iamClient.add(bucket.getName()))
                .toList();

        List<Bucket> iamObjectStores = this.executor.supply(suppliers);

        return this.mapper.fromObjectStores(iamObjectStores);
    }

    public List<IaasObjectStore> put(Collection<? extends UpdateIaasObjectStore> objectStores) {
        List<Supplier<Bucket>> suppliers = objectStores.stream()
                .filter(Objects::nonNull)
                .map(bucket -> (Supplier<Bucket>) () -> iamClient.put(bucket.getName()))
                .toList();

        List<Bucket> iamObjectStores = this.executor.supply(suppliers);

        return this.mapper.fromObjectStores(iamObjectStores);
    }

    public void delete(Collection<String> ids) {
        List<Runnable> runnables = ids.stream()
                .filter(Objects::nonNull)
                .map(id -> (Runnable) () -> iamClient.delete(id))
                .toList();

        this.executor.run(runnables);
    }
}
