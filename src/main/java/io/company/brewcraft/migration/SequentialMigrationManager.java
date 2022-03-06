package io.company.brewcraft.migration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Tenant;

// Note: Can be replaced with a parallel task-set that uses blocking Async executor;
public class SequentialMigrationManager implements MigrationManager {
    private static final Logger log = LoggerFactory.getLogger(SequentialMigrationManager.class);

    private TenantRegister tenantReg;
    private MigrationRegister migrationReg;

    public SequentialMigrationManager(TenantRegister register, MigrationRegister mgr) {
        this.tenantReg = register;
        this.migrationReg = mgr;
    }

    @Override
    public void migrate(Tenant tenant) {
        tenantReg.put(tenant);

        log.info("Applying migration to tenant: {}", tenant.getId());
        migrationReg.migrate(tenant);
    }

    @Override
    public void migrateAll(List<Tenant> tenants) {
        TaskSet tasks = new SequentialTaskSet();

        tenants.forEach(id -> tasks.submit(() -> {
            migrate(id);
        }));

        log.info("{} tenants migrated successfully", tasks.getResults().size());
        if (tasks.getErrors().size() > 0) {
            log.error("Failed to migrate {} tenants", tasks.getErrors().size());
        }

        int i = 0;
        for (Exception e : tasks.getErrors()) {
            log.error("{}: Failed to migrate tenant because: {}", i++, e);
        }
    }
}