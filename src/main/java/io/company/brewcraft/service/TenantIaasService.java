package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasAuthDeleteResult;
import io.company.brewcraft.model.TenantIaasAuthResources;
import io.company.brewcraft.model.TenantIaasDeleteResult;
import io.company.brewcraft.model.TenantIaasIdpDeleteResult;
import io.company.brewcraft.model.TenantIaasIdpResources;
import io.company.brewcraft.model.TenantIaasResources;
import io.company.brewcraft.model.TenantIaasVfsDeleteResult;
import io.company.brewcraft.model.TenantIaasVfsResources;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.model.UpdateTenant;
import io.company.brewcraft.service.mapper.TenantIaasIdpTenantMapper;

public class TenantIaasService {
    private TenantIaasAuthService authService;
    private TenantIaasIdpService idpService;
    private TenantIaasVfsService vfsService;

    private TenantIaasIdpTenantMapper mapper;

    public TenantIaasService(TenantIaasAuthService authService, TenantIaasIdpService idpService, TenantIaasVfsService vfsService, TenantIaasIdpTenantMapper mapper) {
        this.authService = authService;
        this.idpService = idpService;
        this.vfsService = vfsService;
        this.mapper = mapper;
    }

    public List<TenantIaasResources> get(Set<UUID> tenantIds) {
        Set<String> idpTenantsIds = mapper.toIaasTenantIds(tenantIds);

        List<TenantIaasAuthResources> authResources = this.authService.get(idpTenantsIds);
        List<TenantIaasIdpResources> idpResources = this.idpService.get(idpTenantsIds);
        List<TenantIaasVfsResources> vfsResources = this.vfsService.get(idpTenantsIds);

        return map(idpResources, authResources, vfsResources);
    }

    public List<TenantIaasResources> add(List<Tenant> tenants) {
        List<BaseIaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<TenantIaasAuthResources> authResources = this.authService.add(idpTenants);

        Iterator<TenantIaasAuthResources> authResourceIt = authResources.iterator();
        idpTenants.forEach(idpTenant -> idpTenant.setIaasRole(authResourceIt.next().getRole()));

        List<TenantIaasIdpResources> idpResources = this.idpService.add(idpTenants);
        List<TenantIaasVfsResources> vfsResources = this.vfsService.add(idpTenants);

        return map(idpResources, authResources, vfsResources);
    }

    public List<TenantIaasResources> put(List<? extends UpdateTenant> tenants) {
        List<UpdateIaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<TenantIaasAuthResources> authResources = this.authService.put(idpTenants);

        Iterator<TenantIaasAuthResources> authResourceIt = authResources.iterator();
        idpTenants.forEach(idpTenant -> idpTenant.setIaasRole(authResourceIt.next().getRole()));

        List<TenantIaasIdpResources> idpResources = this.idpService.put(idpTenants);
        List<TenantIaasVfsResources> vfsResources = this.vfsService.put(idpTenants);

        return map(idpResources, authResources, vfsResources);
    }

    public TenantIaasDeleteResult delete(Set<UUID> tenantIds) {
        Set<String> iaasTenantsIds = mapper.toIaasTenantIds(tenantIds);

        TenantIaasVfsDeleteResult vfsDelete = this.vfsService.delete(iaasTenantsIds);
        TenantIaasIdpDeleteResult idpDelete = this.idpService.delete(iaasTenantsIds);
        TenantIaasAuthDeleteResult authDelete = this.authService.delete(iaasTenantsIds);

        return new TenantIaasDeleteResult(authDelete, idpDelete, vfsDelete);
    }

    private List<TenantIaasResources> map(List<TenantIaasIdpResources> idpResources, List<TenantIaasAuthResources> authResources, List<? extends TenantIaasVfsResources> vfsResources) {
        Iterator<TenantIaasIdpResources> idpIterator = idpResources.iterator();
        Iterator<TenantIaasAuthResources> authIterator = authResources.iterator();

        return vfsResources.stream()
                           .map(vfsResource -> new TenantIaasResources(authIterator.next(), idpIterator.next(), vfsResource))
                           .toList();
    }
}
