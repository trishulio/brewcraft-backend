package io.company.brewcraft.security.session;

import io.company.brewcraft.model.Tenant;

public interface TenantContext {
    Tenant getTenant();
}
