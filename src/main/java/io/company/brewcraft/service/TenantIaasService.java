package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;

import io.company.brewcraft.model.IaasTenant;
import io.company.brewcraft.model.TenantIaasResources;
import io.company.brewcraft.model.TenantIaasVfsResources;
import io.company.brewcraft.service.impl.IdpTenantIaasRepository;

public class TenantIaasService {
    private TenantIaasVfsService vfsService;
    private IdpTenantIaasRepository idpTenantRepo;;

    public TenantIaasService(TenantIaasVfsService vfsService, IdpTenantIaasRepository idpTenantRepo) {
        this.vfsService = vfsService;
        this.idpTenantRepo = idpTenantRepo;
    }

    public List<TenantIaasResources> get(List<? extends IaasTenant> tenants) {
        List<TenantIaasVfsResources> vfsResources = this.vfsService.get(tenants);

        return map(vfsResources);
    }

    public List<TenantIaasResources> put(List<? extends IaasTenant> tenants) {
        List<TenantIaasVfsResources> vfsResources = this.vfsService.put(tenants);

        Iterator<? extends IaasTenant> iterator = tenants.iterator();
        vfsResources.forEach(vfsResource -> iterator.next().setIaasRole(vfsResource.getRole()));
        this.idpTenantRepo.put(tenants);

        return map(vfsResources);

    }

    public void delete(List<? extends IaasTenant> tenants) {
        List<String> tenantIaasIds = tenants.stream().map(IaasTenant::getIaasId).toList();
        this.idpTenantRepo.delete(tenantIaasIds);
        this.vfsService.delete(tenants);
    }

    private List<TenantIaasResources> map(List<? extends TenantIaasVfsResources> vfsResources) {
        return vfsResources.stream()
                            .map(vfsResource -> new TenantIaasResources(vfsResource))
                            .toList();
    }
}
