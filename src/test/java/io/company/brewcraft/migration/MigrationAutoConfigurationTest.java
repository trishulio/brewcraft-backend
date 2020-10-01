package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MigrationAutoConfigurationTest {

    private MigrationAutoConfiguration config;

    @BeforeEach
    public void init() {
        config = new MigrationAutoConfiguration();
    }

    @Test
    public void testRandomGenerator_ReturnsInstanceOfRandonGeneratorImpl() {
        RandomGenerator rand = config.randomGenerator();
        assertTrue(rand instanceof RandomGeneratorImpl);
    }

    @Test
    public void testTenantRegister_ReturnsInstanceOfFlywayTenantRegister() {
        TenantRegister register = config.tenantRegister(null, null, null, null, null, null);
        assertTrue(register instanceof FlywayTenantRegister);
    }

    @Test
    public void testMigrationMgr_ReturnsInstanceOfFlywayMigrationManager() {
        MigrationManager mgr = config.migrationMgr(null);
        assertTrue(mgr instanceof FlywayMigrationManager);
    }
}
