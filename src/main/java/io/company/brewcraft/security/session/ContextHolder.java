package io.company.brewcraft.security.session;

public interface ContextHolder {
    PrincipalContext getPrincipalContext();

    TenantContext getTenantContext();
}
