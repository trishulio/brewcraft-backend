package io.company.brewcraft.service;

import java.util.List;

import org.springframework.core.io.VfsResource;

import io.company.brewcraft.model.IaasDocumentTemplates;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasRole;
import io.company.brewcraft.model.TenantIaasVfsResources;
import io.company.brewcraft.model.TenantObjectStore;
import io.company.brewcraft.model.TenantPolicy;

public class IaasVfsService {

    private TenantPolicyService policyService;
    private TenantIaasRoleService roleService;
    private TenantObjectStoreService objectStoreService;
    private IaasDocumentTemplates templates;
    
    public IaasVfsService(TenantPolicyService policyService, TenantIaasRoleService roleService, TenantObjectStoreService objectStoreService, IaasDocumentTemplates templates) {
        this.policyService = policyService;
        this.roleService = roleService;
        this.objectStoreService = objectStoreService;
        this.templates = templates;
    }
    
    public List<TenantIaasVfsResources> putResources(List<Tenant> tenants) {
        List<TenantIaasVfsResources> additions = tenants.stream()
                                                        .filter(tenant -> tenant != null)
                                                        .map(tenant -> {
                                                            String tenantId = tenant.getId().toString();
                                                            TenantObjectStore objectStore = new TenantObjectStore();
                                                            objectStore.setName(this.templates.getTenantVfsBucketName(tenantId));
                                                            objectStore.setTenant(tenant);
                                                            
                                                            TenantPolicy policy = new TenantPolicy();
                                                            
                                                            TenantIaasRole role = new TenantIaasRole();
                                                            role.setRoleName(templates.getTenantIaasRoleName(tenantId));
                                                            role.setTenant(tenant);
                                                            role.setPolicies(policies);
                                                            
                                                            new TenantIaasVfsResources(null, null)
                                                        })
    }
}
