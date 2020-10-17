package io.company.brewcraft.migration;

public interface MigrationRegister {

    void migrate();

    void migrate(String tenantId);

    boolean isMigrated(String tenantId);
}
