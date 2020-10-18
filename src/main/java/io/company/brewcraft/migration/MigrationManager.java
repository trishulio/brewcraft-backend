package io.company.brewcraft.migration;

public interface MigrationManager {

    public void migrateAll();

    public void migrate(String tenantId);

}
