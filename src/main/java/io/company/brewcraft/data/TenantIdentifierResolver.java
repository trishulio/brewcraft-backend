package io.company.brewcraft.data;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import io.company.brewcraft.security.session.ContextHolder;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final String defaultTenantId = "null";

    private ContextHolder contextHolder;

    public TenantIdentifierResolver(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return contextHolder.getPrincipalContext() != null ? contextHolder.getPrincipalContext().getTenantId() : defaultTenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
