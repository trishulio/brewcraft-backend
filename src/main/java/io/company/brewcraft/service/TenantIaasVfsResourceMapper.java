package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.TenantIaasVfsResources;

public class TenantIaasVfsResourceMapper {
    public static final TenantIaasVfsResourceMapper INSTANCE = new TenantIaasVfsResourceMapper();

    protected TenantIaasVfsResourceMapper() {}

    public List<TenantIaasVfsResources> fromComponents(List<IaasObjectStore> objectStores, List<IaasPolicy> policies) {
        int objectStoreCount = objectStores.size();
        boolean areSameSize = objectStoreCount == policies.size();

        if (!areSameSize) {
            throw new IllegalArgumentException("Resource Lists are not of the same size");
        }

        // TODO: Assumes that the objectStores and policies are ordered.
        Iterator<IaasObjectStore> objectStoresIterator = objectStores.iterator();
        Iterator<IaasPolicy> policiesIterator = policies.iterator();

        List<TenantIaasVfsResources> resources = Stream.generate(() -> new TenantIaasVfsResources(
                                                                             objectStoresIterator.next(),
                                                                             policiesIterator.next()
                                                                     )
                                                                 )
                                                                 .limit(objectStoreCount)
                                                                 .toList();
         return resources;
    }
}
