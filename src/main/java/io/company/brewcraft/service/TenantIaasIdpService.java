package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.TenantIaasIdpResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;

public class TenantIaasIdpService {
    private IaasIdpTenantService idpService;
    private TenantIaasIdpResourcesMapper mapper;

    public TenantIaasIdpService(IaasIdpTenantService idpService, TenantIaasIdpResourcesMapper mapper) {
        this.idpService = idpService;
        this.mapper = mapper;
    }
    
    public List<TenantIaasIdpResources> get(List<IaasIdpTenant> tenants) {
        Set<String> tenantIaasIds = tenants.stream().map(tenant -> tenant.getId()).collect(Collectors.toSet());
        List<IaasIdpTenant> idpTenants = this.idpService.getAll(tenantIaasIds);
        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> add(List<? extends BaseIaasIdpTenant> tenants) {
        @SuppressWarnings("unchecked")
        List<IaasIdpTenant> idpTenants = this.idpService.add((List<BaseIaasIdpTenant>) tenants);
        
        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> put(List<? extends UpdateIaasIdpTenant> tenants) {
        @SuppressWarnings("unchecked")
        List<IaasIdpTenant> idpTenants = this.idpService.put((List<UpdateIaasIdpTenant>) tenants);

        return this.mapper.fromComponents(idpTenants);
    }

    public void delete(List<IaasIdpTenant> iaasTenants) {
        Set<String> tenantIaasIds = iaasTenants.stream().map(iaasTenant -> iaasTenant.getId()).collect(Collectors.toSet());
        
        this.idpService.delete(tenantIaasIds);
    }

    public boolean exist(String tenantIaasId) {
        return this.idpService.exist(tenantIaasId);
    }
}
