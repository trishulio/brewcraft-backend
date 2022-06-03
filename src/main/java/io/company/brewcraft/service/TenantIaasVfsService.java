package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasObjectStoreCorsConfiguration;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.TenantIaasVfsDeleteResult;
import io.company.brewcraft.model.TenantIaasVfsResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.model.UpdateIaasObjectStore;
import io.company.brewcraft.model.UpdateIaasPolicy;
import io.company.brewcraft.model.UpdateIaasRolePolicyAttachment;

public class TenantIaasVfsService {
    private TenantIaasVfsResourceMapper mapper;
    private IaasPolicyService policyService;
    private IaasRolePolicyAttachmentService rolePolicyAttachmentService;
    private IaasObjectStoreService objectStoreService;
    private IaasObjectStoreCorsConfigService bucketCrossOriginConfigService;

    private TenantIaasResourceBuilder resourceBuilder;

    public TenantIaasVfsService(TenantIaasVfsResourceMapper mapper, IaasPolicyService policyService, IaasObjectStoreService objectStoreService, IaasRolePolicyAttachmentService rolePolicyAttachmentService, IaasObjectStoreCorsConfigService bucketCrossOriginConfigService, TenantIaasResourceBuilder resourceBuilder) {
        this.mapper = mapper;
        this.policyService = policyService;
        this.objectStoreService = objectStoreService;
        this.rolePolicyAttachmentService = rolePolicyAttachmentService;
        this.bucketCrossOriginConfigService = bucketCrossOriginConfigService;
        this.resourceBuilder = resourceBuilder;
    }

    public List<TenantIaasVfsResources> get(List<IaasIdpTenant> tenants) {
        Set<String> policyIds = new HashSet<>();
        Set<String> objectStoreIds = new HashSet<>();

        tenants.stream()
               .forEach(iaasIdpTenant -> {
                   String policyId = this.resourceBuilder.getVfsPolicyName(iaasIdpTenant);
                   policyIds.add(policyId);

                   String objectStoreId = this.resourceBuilder.getObjectStoreName(iaasIdpTenant);
                   objectStoreIds.add(objectStoreId);
                });

        List<IaasPolicy> policies = this.policyService.getAll(policyIds);
        List<IaasObjectStore> objectStores = this.objectStoreService.getAll(objectStoreIds);

        return mapper.fromComponents(objectStores, policies);
    }

    public List<TenantIaasVfsResources> add(List<? extends BaseIaasIdpTenant> tenants) {
        List<BaseIaasObjectStore> objectStoreAdditions = new ArrayList<>(tenants.size());
        List<BaseIaasPolicy> policiesAdditions = new ArrayList<>(tenants.size());

        tenants.forEach(tenant -> {
            BaseIaasObjectStore objectStore = this.resourceBuilder.buildObjectStore(tenant);
            objectStoreAdditions.add(objectStore);

            BaseIaasPolicy policy = this.resourceBuilder.buildVfsPolicy(tenant);
            policiesAdditions.add(policy);
        });

        List<IaasPolicy> policies = this.policyService.add(policiesAdditions);
        List<IaasObjectStore> objectStores = this.objectStoreService.add(objectStoreAdditions);

        Iterator<IaasPolicy> policiesIterator = policies.iterator();

        List<BaseIaasRolePolicyAttachment> attachmentAdditions = tenants.stream()
                .map(tenant -> this.resourceBuilder.buildAttachment(tenant.getIaasRole(), policiesIterator.next()))
                .map(o -> (BaseIaasRolePolicyAttachment) o)
                .toList();

        List<IaasRolePolicyAttachment> attachments = this.rolePolicyAttachmentService.add(attachmentAdditions);

        List<IaasObjectStoreCorsConfiguration> crossOriginConfigs = tenants.stream()
                .map(tenant -> this.resourceBuilder.buildBucketCrossOriginConfiguration(tenant))
                .map(o -> o)
                .toList();

        List<IaasObjectStoreCorsConfiguration> bucketCrossOriginConfigs = this.bucketCrossOriginConfigService.add(crossOriginConfigs);

        return this.mapper.fromComponents(objectStores, policies);
    }

    public List<TenantIaasVfsResources> put(List<? extends UpdateIaasIdpTenant> tenants) {
        List<UpdateIaasObjectStore> objectStoreUpdates = new ArrayList<>(tenants.size());
        List<UpdateIaasPolicy> policiesUpdates = new ArrayList<>(tenants.size());

        tenants.forEach(tenant -> {
            UpdateIaasObjectStore objectStore = this.resourceBuilder.buildObjectStore(tenant);
            objectStoreUpdates.add(objectStore);

            UpdateIaasPolicy policy = this.resourceBuilder.buildVfsPolicy(tenant);
            policiesUpdates.add(policy);
        });

        List<IaasPolicy> policies = this.policyService.put(policiesUpdates);
        List<IaasObjectStore> objectStores = this.objectStoreService.put(objectStoreUpdates);

        Iterator<IaasPolicy> policiesIterator = policies.iterator();

        List<UpdateIaasRolePolicyAttachment> attachmentUpdates = tenants.stream()
                .map(tenant -> this.resourceBuilder.buildAttachment(tenant.getIaasRole(), policiesIterator.next()))
                .map(o -> (UpdateIaasRolePolicyAttachment) o)
                .toList();

        List<IaasRolePolicyAttachment> attachments = this.rolePolicyAttachmentService.put(attachmentUpdates);

        List<IaasObjectStoreCorsConfiguration> crossOriginConfigs = tenants.stream()
                .map(tenant -> this.resourceBuilder.buildBucketCrossOriginConfiguration(tenant))
                .map(o -> o)
                .toList();

        List<IaasObjectStoreCorsConfiguration> bucketCrossOriginConfigs = this.bucketCrossOriginConfigService.put(crossOriginConfigs);

        return this.mapper.fromComponents(objectStores, policies);
    }

    public TenantIaasVfsDeleteResult delete(List<IaasIdpTenant> iaasIdpTenants) {
        Set<String> objectStoreIds = new HashSet<>();
        Set<String> policyIds = new HashSet<>();
        Set<IaasRolePolicyAttachmentId> attachmentIds = new HashSet<>();

        iaasIdpTenants
        .stream()
        .forEach(iaasIdpTenant -> {
            String policyId = this.resourceBuilder.getVfsPolicyName(iaasIdpTenant);
            policyIds.add(policyId);

            IaasRolePolicyAttachmentId attachmentId = this.resourceBuilder.buildVfsAttachmentId(iaasIdpTenant);
            attachmentIds.add(attachmentId);

            String objectStoreId = this.resourceBuilder.getObjectStoreName(iaasIdpTenant);
            objectStoreIds.add(objectStoreId);
         });

        this.rolePolicyAttachmentService.delete(attachmentIds);
        this.bucketCrossOriginConfigService.delete(objectStoreIds);
        long policyCount = this.policyService.delete(policyIds);
        long objectStoreCount = this.objectStoreService.delete(objectStoreIds);

        return new TenantIaasVfsDeleteResult(policyCount, objectStoreCount);
    }
}
