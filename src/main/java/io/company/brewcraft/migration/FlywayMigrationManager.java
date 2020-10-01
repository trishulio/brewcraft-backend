package io.company.brewcraft.migration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayMigrationManager implements MigrationManager {
    private static final Logger logger = LoggerFactory.getLogger(FlywayMigrationManager.class);

    private TenantRegister register;

    public FlywayMigrationManager(TenantRegister register) {
        this.register = register;
    }

    @Override
    @PostConstruct
    public void migrateAll() {
        register.registerApp();

        List<String> testTenants = new ArrayList<String>();
        testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab67");
        testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab68");

        testTenants.forEach(id -> migrate(id));
    }

    @Override
    
    public void migrate(String tenantId) {
        // TODO: Will we ever have a user without a schema?
        // In case of failure, we should probably roll-back to delete the user
        // and have a clean state instead of creating workflow branches.
        // See method named: idealMigrationSampleMethod
        boolean userExists = register.userExists(tenantId);
        if (!userExists) {
            logger.debug("User '{}' does not exist", tenantId);
            register.registerUser(tenantId);
            logger.debug("Registered user '{}'", tenantId);
        }

        register.registerTenant(tenantId);
        logger.debug("Tenant registration successful for {}", tenantId);
    }

    // Will throw an exception because the corresponding register methods are not
    // implemented yet.
    public void idealMigrationSampleMethod(String tenantId) {
        try {
            register.registerUser(tenantId);
            register.registerTenant(tenantId);
        } catch (Exception e) {
            if (register.isRegisteredTenant(tenantId)) {
                register.deregister(tenantId);
            }

            if (register.userExists(tenantId)) {
                register.removeUser(tenantId);
            }

            throw e;
        }
    }
}