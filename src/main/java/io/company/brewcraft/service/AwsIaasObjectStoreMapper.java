package io.company.brewcraft.service;

import java.util.List;

import com.amazonaws.services.s3.model.Bucket;

import io.company.brewcraft.model.IaasObjectStore;

public class AwsIaasObjectStoreMapper {
    private LocalDateTimeMapper dtMapper;
    
    public AwsIaasObjectStoreMapper(LocalDateTimeMapper dtMapper) {
        this.dtMapper = dtMapper;
    }

    public List<IaasObjectStore> fromObjectStores(List<Bucket> iamPolicies) {
        List<IaasObjectStore> policies = null;
        
        if (iamPolicies != null) {
            policies = iamPolicies.stream().map(this::fromObjectStore).toList();
        }
        
        return policies;
    }
    
    public IaasObjectStore fromObjectStore(Bucket iamObjectStore) {
        IaasObjectStore objectStore = null;
        
        if (iamObjectStore != null) {
            objectStore = new IaasObjectStore();
            objectStore.setName(iamObjectStore.getName());
            objectStore.setCreatedAt(this.dtMapper.fromUtilDate(iamObjectStore.getCreationDate()));
        }
        
        return objectStore;
    }
}
