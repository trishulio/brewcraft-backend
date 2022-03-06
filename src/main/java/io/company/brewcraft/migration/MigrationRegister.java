package io.company.brewcraft.migration;

import io.company.brewcraft.model.Tenant;

public interface MigrationRegister {
    void migrate(Tenant tenant);

    boolean isMigrated(Tenant tenant);
}
