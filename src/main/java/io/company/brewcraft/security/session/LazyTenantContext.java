package io.company.brewcraft.security.session;

import java.util.UUID;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.service.impl.TenantManagementService;

public class LazyTenantContext implements TenantContext {
    private TenantManagementService service;
    private IaasIdpTenant idpTenant;
    private Tenant tenant;
    private UUID tenantId;

    public LazyTenantContext(TenantManagementService service, IaasIdpTenant idpTenant, UUID tenantId) {
        this.service = service;
        this.idpTenant = idpTenant;
        this.tenantId = tenantId;
    }

    @Override
    public Tenant getTenant() {
        if (this.tenant == null) {
            this.tenant = this.service.get(this.tenantId);
        }

        return this.tenant;
    }

    @Override
    public IaasIdpTenant getIaasIdpTenant() {
       return this.idpTenant;
    }

}
