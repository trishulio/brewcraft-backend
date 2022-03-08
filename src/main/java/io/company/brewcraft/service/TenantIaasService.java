package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasAuthResources;
import io.company.brewcraft.model.TenantIaasIdpResources;
import io.company.brewcraft.model.TenantIaasResources;
import io.company.brewcraft.model.TenantIaasVfsResources;
import io.company.brewcraft.service.mapper.IaasIdpTenantMapper;

public class TenantIaasService {
    private TenantIaasAuthService authService;
    private TenantIaasIdpService idpService;
    private TenantIaasVfsService vfsService;

    private IaasIdpTenantMapper mapper;

    public TenantIaasService(TenantIaasAuthService authService, TenantIaasIdpService idpService, TenantIaasVfsService vfsService, IaasIdpTenantMapper mapper) {
        this.authService = authService;
        this.idpService = idpService;
        this.vfsService = vfsService;
        this.mapper = mapper;
    }

    public List<TenantIaasResources> get(List<Tenant> tenants) {
        List<IaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<TenantIaasAuthResources> authResources = this.authService.get(idpTenants);
        List<TenantIaasIdpResources> idpResources = this.idpService.get(idpTenants);
        List<TenantIaasVfsResources> vfsResources = this.vfsService.get(idpTenants);

        return map(idpResources, authResources, vfsResources);
    }

    public List<TenantIaasResources> add(List<Tenant> tenants) {
        List<IaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<TenantIaasAuthResources> authResources = this.authService.add(idpTenants);

        Iterator<TenantIaasAuthResources> authResourceIt = authResources.iterator();
        idpTenants.forEach(idpTenant -> idpTenant.setIaasRole(authResourceIt.next().getRole()));

        List<TenantIaasIdpResources> idpResources = this.idpService.add(idpTenants);
        List<TenantIaasVfsResources> vfsResources = this.vfsService.add(idpTenants);

        return map(idpResources, authResources, vfsResources);
    }

    public List<TenantIaasResources> put(List<Tenant> tenants) {
        List<IaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<TenantIaasAuthResources> authResources = this.authService.put(idpTenants);

        Iterator<TenantIaasAuthResources> authResourceIt = authResources.iterator();
        idpTenants.forEach(idpTenant -> idpTenant.setIaasRole(authResourceIt.next().getRole()));

        List<TenantIaasIdpResources> idpResources = this.idpService.put(idpTenants);
        List<TenantIaasVfsResources> vfsResources = this.vfsService.put(idpTenants);

        return map(idpResources, authResources, vfsResources);
    }

    public void delete(List<Tenant> tenants) {
        List<IaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        this.vfsService.delete(idpTenants);
        this.idpService.delete(idpTenants);
        this.authService.delete(idpTenants);
    }

    private List<TenantIaasResources> map(List<TenantIaasIdpResources> idpResources, List<TenantIaasAuthResources> authResources, List<? extends TenantIaasVfsResources> vfsResources) {
        return vfsResources.stream()
                            .map(vfsResource -> new TenantIaasResources(vfsResource))
                            .toList();
    }
}
