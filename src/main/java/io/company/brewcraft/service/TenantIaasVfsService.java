package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasVfsResources;

public class TenantIaasVfsService {
    private AwsArnMapper arnMapper;
    private AwsDocumentTemplates templates;
    private TenantIaasVfsResourceMapper mapper;
    private IaasPolicyService policyService;
    private IaasRoleService roleService;
    private IaasRolePolicyAttachmentService rolePolicyAttachmentService;
    private IaasObjectStoreService objectStoreService;

    public TenantIaasVfsService(AwsArnMapper arnMapper, TenantIaasVfsResourceMapper mapper, IaasPolicyService policyService, IaasRoleService roleService, IaasObjectStoreService objectStoreService, IaasRolePolicyAttachmentService rolePolicyAttachmentService, AwsDocumentTemplates templates) {
        this.arnMapper = arnMapper;
        this.templates = templates;
        this.mapper = mapper;
        this.policyService = policyService;
        this.roleService = roleService;
        this.objectStoreService = objectStoreService;
        this.rolePolicyAttachmentService = rolePolicyAttachmentService;
    }

    public List<TenantIaasVfsResources> get(List<Tenant> tenants) {
        // Hack: We are using ARN mapper to generate value for the policy. Ideally, we should fetch the ARN by retrieving a policy object
        // from the Iaas provider. If there is a misconfiguration in the application, the generated ARN might not be accurate. Keep
        // an eye-out for this operation to ensure it's not buggy.
        Set<String> objectStoreIds = new HashSet<>();
        Set<String> policyIds = new HashSet<>();
        Set<String> roleIds = new HashSet<>();
        Set<IaasRolePolicyAttachmentId> attachmentIds = new HashSet<>();

        tenants
        .stream()
        .map(tenant -> tenant.getId().toString())
        .forEach(tenantId -> {
            String roleName = this.templates.getTenantIaasRoleName(tenantId);
            String policyArn = this.arnMapper.getPolicyArn(this.templates.getTenantVfsPolicyName(tenantId));
            String objectStoreId = this.templates.getTenantVfsBucketName(tenantId);
            IaasRolePolicyAttachmentId attachmentId = new IaasRolePolicyAttachmentId(policyArn, roleName);

            objectStoreIds.add(objectStoreId);
            policyIds.add(policyArn);
            roleIds.add(roleName);
            attachmentIds.add(attachmentId);
         });

        List<IaasPolicy> policies = this.policyService.getAll(policyIds);
        List<IaasRole> roles = this.roleService.getAll(roleIds);
        List<IaasObjectStore> objectStores = this.objectStoreService.getAll(objectStoreIds);
        List<IaasRolePolicyAttachment> attachments = this.rolePolicyAttachmentService.getAll(attachmentIds);

        Map<String, TenantIaasVfsResources> tenantVfsResources = new HashMap<>();

        objectStores.forEach(objectStore -> {
            TenantIaasVfsResources vfsResources = new TenantIaasVfsResources();
            vfsResources.setObjectStore(objectStore);
            String tenantId = this.templates.getTenantIdFromTenantVfsBucketName(objectStore.getName());
            tenantVfsResources.put(tenantId, vfsResources);
        });

        policies.forEach(policy -> {
            String tenantId = this.templates.getTenantIdFromTenantVfsPolicyName(policy.getName());
            TenantIaasVfsResources vfsResources = tenantVfsResources.get(tenantId);
            vfsResources.setPolicy(policy);
        });

        roles.forEach(role -> {
            String tenantId = this.templates.getTenantIdFromTenantIaasRoleName(role.getName());
            TenantIaasVfsResources vfsResources = tenantVfsResources.get(tenantId);
            vfsResources.setRole(role);
        });

        // Leaving out including the attachments since they are redundant data.
        // attachments.forEach(attachment -> {
        //    TenantIaasVfsResources vfsResources = tenantVfsResources.get(attachment.getTenantId());
        //    vfsResources.setRolePolicyAttachment(attachment);
        // });

        return new ArrayList<>(tenantVfsResources.values());
    }

    public List<TenantIaasVfsResources> put(List<Tenant> tenants) {
        List<BaseIaasObjectStore> objectStoreAdditions = new ArrayList<>(tenants.size());
        List<BaseIaasPolicy> policieAdditions = new ArrayList<>(tenants.size());
        List<BaseIaasRole> roleAdditions = new ArrayList<>(tenants.size());

        tenants.forEach(tenant -> {
            String tenantId = tenant.getId().toString();

            BaseIaasObjectStore objectStore = new IaasObjectStore();
            objectStore.setName(this.templates.getTenantVfsBucketName(tenantId));

            BaseIaasPolicy policy = new IaasPolicy();
            policy.setDescription(this.templates.getTenantVfsPolicyDescription(tenantId));
            policy.setDocument(this.templates.getTenantBucketPolicyDoc(tenantId));
            policy.setName(this.templates.getTenantVfsPolicyName(tenantId));

            BaseIaasRole role = new IaasRole();
            role.setName(this.templates.getTenantIaasRoleName(tenantId));
            role.setDescription(this.templates.getTenantIaasRoleDescription(tenantId));
            role.setAssumePolicyDocument(this.templates.getCognitoIdAssumeRolePolicyDoc());

            objectStoreAdditions.add(objectStore);
            policieAdditions.add(policy);
            roleAdditions.add(role);
        });

        List<IaasObjectStore> objectStores = this.objectStoreService.add(objectStoreAdditions);
        List<IaasPolicy> policies = this.policyService.add(policieAdditions);
        List<IaasRole> roles = this.roleService.add(roleAdditions);

        Iterator<IaasPolicy> policiesIterator = policies.iterator();
        Iterator<IaasRole> rolesIterator = roles.iterator();

        List<BaseIaasRolePolicyAttachment> attachmentAdditions = tenants.stream()
                .map(tenant -> {
                    BaseIaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment();
                    // Note: This code assumes that the returned policies and roles are in the same order as they were
                    // created in. If that assumption is violated, this code will attach incorrect policies to the
                    // roles.
                    attachment.setIaasRole(rolesIterator.next());
                    attachment.setIaasPolicy(policiesIterator.next());

                    return attachment;
                })
                .toList();

        List<IaasRolePolicyAttachment> attachments = this.rolePolicyAttachmentService.add(attachmentAdditions);

        return this.mapper.fromComponents(objectStores, policies, roles, attachments);
    }

    public void delete(List<Tenant> tenants) {
        // Hack: We are using ARN mapper to generate value for the policy. Ideally, we should fetch the ARN by retrieving a policy object
        // from the Iaas provider. If there is a misconfiguration in the application, the generated ARN might not be accurate. Keep
        // an eye-out for this operation to ensure it's not buggy
        Set<String> objectStoreIds = new HashSet<>();
        Set<String> policyIds = new HashSet<>();
        Set<String> roleIds = new HashSet<>();
        Set<IaasRolePolicyAttachmentId> attachmentIds = new HashSet<>();

        tenants
        .stream()
        .map(tenant -> tenant.getId().toString())
        .forEach(tenantId -> {
            String roleName = this.templates.getTenantIaasRoleName(tenantId);
            String policyArn = this.arnMapper.getPolicyArn(this.templates.getTenantVfsPolicyName(tenantId));
            String objectStoreId = this.templates.getTenantVfsBucketName(tenantId);
            IaasRolePolicyAttachmentId attachmentId = new IaasRolePolicyAttachmentId(policyArn, roleName);

            objectStoreIds.add(objectStoreId);
            policyIds.add(policyArn);
            roleIds.add(roleName);
            attachmentIds.add(attachmentId);
         });

        this.rolePolicyAttachmentService.delete(attachmentIds);
        this.policyService.delete(policyIds);
        this.roleService.delete(roleIds);
        this.objectStoreService.delete(objectStoreIds);
    }
}
