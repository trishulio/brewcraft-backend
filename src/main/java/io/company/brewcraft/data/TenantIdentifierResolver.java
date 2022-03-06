package io.company.brewcraft.data;

import java.util.UUID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private ContextHolder contextHolder;
    private String defaultTenantId;

    public TenantIdentifierResolver(ContextHolder contextHolder, Tenant adminTenant) {
        this.contextHolder = contextHolder;
        this.defaultTenantId = adminTenant.getId().toString();
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = this.defaultTenantId;

        UUID tenantId = null;

        PrincipalContext ctx = contextHolder.getPrincipalContext();
        if (ctx != null) {
            tenantId = ctx.getTenantId();
        }

        if (tenantId != null) {
            currentTenantId = tenantId.toString();
        }

        return currentTenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
