package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.amazonaws.services.s3.model.Bucket;

import io.company.brewcraft.model.IaasObjectStore;

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
        List<Supplier<Bucket>> suppliers = ids.stream()
                .filter(id -> id != null)
                .map(id -> (Supplier<Bucket>) () -> iamClient.get(id))
                .toList();
        
        List<Bucket> iamObjectStores = this.executor.supply(suppliers);
        
        return this.mapper.fromObjectStores(iamObjectStores);
    }
    
    public List<IaasObjectStore> add(Collection<IaasObjectStore> objectStores) {
        List<Supplier<Bucket>> suppliers = objectStores.stream()
                .filter(bucket -> bucket != null)
                .map(bucket -> (Supplier<Bucket>) () -> iamClient.add(bucket.getName()))
                .toList();
        
        List<Bucket> iamObjectStores = this.executor.supply(suppliers);
        
        return this.mapper.fromObjectStores(iamObjectStores);
    }
    
    public void delete(Collection<String> ids) {
        List<Runnable> runnables = ids.stream()
                .filter(id -> id != null)
                .map(id -> (Runnable) () -> iamClient.delete(id))
                .toList();
        
        this.executor.run(runnables);
    }
}
