package io.company.brewcraft.service;

import java.util.List;
import java.util.stream.Collectors;

import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantIaasResources;
import io.company.brewcraft.model.TenantIaasVfsResources;

public class TenantIaasService {
    private TenantIaasVfsService vfsService;

    public TenantIaasService(TenantIaasVfsService vfsService) {
        this.vfsService = vfsService;
    }

    public List<TenantIaasResources> get(List<Tenant> tenants) {
        List<TenantIaasVfsResources> vfsResources = this.vfsService.get(tenants);

        return map(vfsResources);
    }

    public List<TenantIaasResources> put(List<Tenant> tenants) {
        List<TenantIaasVfsResources> vfsResources = this.vfsService.put(tenants);

        return map(vfsResources);

    }

    public void delete(List<Tenant> tenants) {
        this.vfsService.delete(tenants);
    }

    private List<TenantIaasResources> map(List<TenantIaasVfsResources> vfsResources) {
        return vfsResources.stream()
                            .map(vfsResource -> new TenantIaasResources(vfsResource))
                            .toList();
    }
}
