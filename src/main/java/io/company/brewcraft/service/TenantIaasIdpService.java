package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.TenantIaasIdpDeleteResult;
import io.company.brewcraft.model.TenantIaasIdpResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;

public class TenantIaasIdpService {
    private IaasIdpTenantService idpService;
    private TenantIaasIdpResourcesMapper mapper;

    public TenantIaasIdpService(IaasIdpTenantService idpService, TenantIaasIdpResourcesMapper mapper) {
        this.idpService = idpService;
        this.mapper = mapper;
    }

    public List<TenantIaasIdpResources> get(Set<String> iaasIdpTenantIds) {
        List<IaasIdpTenant> idpTenants = this.idpService.getAll(iaasIdpTenantIds);
        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> add(List<BaseIaasIdpTenant> tenants) {
        List<IaasIdpTenant> idpTenants = this.idpService.add(tenants);

        return this.mapper.fromComponents(idpTenants);
    }

    public List<TenantIaasIdpResources> put(List<UpdateIaasIdpTenant> tenants) {
        List<IaasIdpTenant> idpTenants = this.idpService.put(tenants);

        return this.mapper.fromComponents(idpTenants);
    }

    public TenantIaasIdpDeleteResult delete(Set<String> iaasIdpTenantIds) {

        long idpCount = this.idpService.delete(iaasIdpTenantIds);

        return new TenantIaasIdpDeleteResult(idpCount);
    }

    public boolean exist(String iaasIdpTenantId) {
        return this.idpService.exist(iaasIdpTenantId);
    }
}
