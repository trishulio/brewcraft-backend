package io.company.brewcraft.migration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequentialMigrationManager implements MigrationManager {
    private static final Logger log = LoggerFactory.getLogger(SequentialMigrationManager.class);

    private TenantRegister register;

    public SequentialMigrationManager(TenantRegister register) {
        this.register = register;
    }

    @Override
    @PostConstruct
    public void migrateAll() {
        // Mock data
        List<String> testTenants = new ArrayList<String>();
        testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab67");
        testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab68");

        migrateAll(new SequentialTaskSet(), testTenants);
    }

    @Override

    public void migrate(String tenantId) {
        if (register.exists(tenantId)) {
            throw new RuntimeException(String.format("Tenant already exists: %s", tenantId));
        }

        register.add(tenantId);
    }

    protected void migrateAll(TaskSet tasks, List<String> tenants) {
        tasks.submit(() -> {
            register.setup();
            return null;
        });

        tenants.forEach(id -> tasks.submit(() -> {
            migrate(id);
            return null;
        }));

        log.info("{} tenants migrated successfully", tasks.getResults().size());
        log.error("Failed to migrate {} tenants", tasks.getErrors().size());

        int i = 0;
        for (Exception e : tasks.getErrors()) {
            log.error("{}: Failed to migrate tenant because: {}", i++, e);
        }
    }
}