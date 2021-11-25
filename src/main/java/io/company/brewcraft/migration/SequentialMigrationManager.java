package io.company.brewcraft.migration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.service.IdpUserRepository;

public class SequentialMigrationManager implements MigrationManager {
    private static final Logger log = LoggerFactory.getLogger(SequentialMigrationManager.class);

    private TenantRegister tenantReg;
    private MigrationRegister migrationReg;
    private IdpUserRepository idpUserRepo;

    public SequentialMigrationManager(TenantRegister register, MigrationRegister mgr, IdpUserRepository idpUserRepo) {
        this.tenantReg = register;
        this.migrationReg = mgr;
        this.idpUserRepo = idpUserRepo;
    }

    @Override
    @PostConstruct
    public void migrateAll() {
        // Mock data
        List<String> testTenants = new ArrayList<String>();
        testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab67");

        migrateAll(new SequentialTaskSet(), testTenants);
    }

    @Override

    public void migrate(String tenantId) {
        if (!tenantReg.exists(tenantId)) {
            log.info("Registering new tenantId: {}", tenantId);
            tenantReg.add(tenantId);
        }

        log.info("Registering idp user group: {}", tenantId);
        idpUserRepo.putUserGroup(tenantId);

        log.info("Applying migration to tenant: {}", tenantId);
        migrationReg.migrate(tenantId);
    }

    protected void migrateAll(TaskSet tasks, List<String> tenants) {
        tasks.submit(() -> {
            migrationReg.migrate();
        });

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