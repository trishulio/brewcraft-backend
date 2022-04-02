package io.company.brewcraft.security.session;

import java.util.UUID;

import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.service.impl.TenantService;

public class LazyTenantContext implements TenantContext {
    private Tenant tenant;
    private UUID tenantId;
    private TenantService service;

    public LazyTenantContext(TenantService service, UUID tenantId) {
        this.service = service;
        this.tenantId = tenantId;
    }

    @Override
    public Tenant getTenant() {
        if (this.tenant == null) {
            this.tenant = this.service.get(this.tenantId);
        }

        return this.tenant;
    }
}
