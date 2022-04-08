package io.company.brewcraft.migration;

import java.util.List;

import io.company.brewcraft.model.Tenant;

public interface MigrationManager {
    void migrateAll(List<Tenant> tenants);

    void migrate(Tenant tenant);
}
