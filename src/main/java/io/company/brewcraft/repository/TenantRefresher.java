package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantAccessor;

public class TenantRefresher implements Refresher<Tenant, TenantAccessor> {
    private static final Logger log = LoggerFactory.getLogger(TenantRefresher.class);

    private final AccessorRefresher<UUID, TenantAccessor, Tenant> refresher;

    public TenantRefresher(AccessorRefresher<UUID, TenantAccessor, Tenant> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<Tenant> tenants) {
    }

    @Override
    public void refreshAccessors(Collection<? extends TenantAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
