package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.TenantIaasIdpResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.service.impl.IdpTenantIaasRepository;

public class TenantIaasIdpService {
    private IdpTenantIaasRepository idpTenantRepo;
    private TenantIaasIdpResourcesMapper mapper;

    public TenantIaasIdpService(IdpTenantIaasRepository idpTenantRepo, TenantIaasIdpResourcesMapper mapper) {
        this.idpTenantRepo = idpTenantRepo;
        this.mapper = mapper;
    }
    
    public List<TenantIaasIdpResources> get(List<IaasIdpTenant> tenants) {
        Set<String> tenantIaasIds = tenants.stream().map(tenant -> tenant.getId()).collect(Collectors.toSet());
        List<IaasIdpTenant> idpTenants = this.idpTenantRepo.get(tenantIaasIds);
        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> add(List<? extends BaseIaasIdpTenant> tenants) {
        List<IaasIdpTenant> idpTenants = this.idpTenantRepo.add(tenants);
        
        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> put(List<? extends UpdateIaasIdpTenant> tenants) {
        List<IaasIdpTenant> idpTenants = this.idpTenantRepo.put(tenants);

        return this.mapper.fromComponents(idpTenants);
    }

    public void delete(List<IaasIdpTenant> iaasTenants) {
        Set<String> tenantIaasIds = iaasTenants.stream().map(iaasTenant -> iaasTenant.getId()).collect(Collectors.toSet());
        
        this.idpTenantRepo.delete(tenantIaasIds);
    }

    public boolean exists(String tenantIaasId) {
        return this.idpTenantRepo.exists(tenantIaasId);
    }
}
