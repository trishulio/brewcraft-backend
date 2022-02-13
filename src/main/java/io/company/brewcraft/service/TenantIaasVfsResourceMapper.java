package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.TenantIaasVfsResources;

public class TenantIaasVfsResourceMapper {
    public List<TenantIaasVfsResources> fromComponents(List<IaasObjectStore> objectStores, List<IaasPolicy> policies, List<IaasRole> roles, List<IaasRolePolicyAttachment> attachments) {
        int objectStoreCount = objectStores.size();
        boolean areSameSize = objectStoreCount == policies.size() &&
                              objectStoreCount == roles.size() &&
                              objectStoreCount == attachments.size();

        if (!areSameSize) {
            throw new IllegalArgumentException("Resource Lists are not of the same size");
        }

        Iterator<IaasObjectStore> objectStoresIterator = objectStores.iterator();
        Iterator<IaasPolicy> policiesIterator = policies.iterator();
        Iterator<IaasRole> rolesIterator = roles.iterator();
        Iterator<IaasRolePolicyAttachment> attachmentsIterator = attachments.iterator();

        List<TenantIaasVfsResources> resources = Stream.generate(() -> new TenantIaasVfsResources(
                                                                             objectStoresIterator.next(),
                                                                             policiesIterator.next(),
                                                                             rolesIterator.next(),
                                                                             attachmentsIterator.next()
                                                                     )
                                                                 )
                                                                 .limit(objectStoreCount)
                                                                 .collect(Collectors.toList());
         return resources;
    }
}
