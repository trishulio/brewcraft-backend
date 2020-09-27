package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.ContextHolderDataSourceManager;
import io.company.brewcraft.data.DataAutoConfiguration;
import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;
import io.company.brewcraft.data.SchemaDataSourceManager;
import io.company.brewcraft.data.TenantDataSourceManager;

public class DataAutoConfigurationTest {

    private DataAutoConfiguration config;

    @BeforeEach
    public void init() {
        config = new DataAutoConfiguration();
    }

    @Test
    public void testDataSourceManager_ReturnsSchemaBasedDataSourceManager() {
        DataSourceManager mgr = config.dataSourceManager(null, null, null);
        assertTrue(mgr instanceof SchemaDataSourceManager);
    }

    @Test
    public void testDsBuilder_ReturnsHikariDataSourceBuilder() {
        DataSourceBuilder builder = config.dsBuilder();
        assertTrue(builder instanceof HikariDataSourceBuilder);
    }

    @Test
    public void testTenantDsManager_ReturnsContextHolderDsManager() {
        TenantDataSourceManager mgr = config.tenantDsManager(null, null);
        assertTrue(mgr instanceof ContextHolderDataSourceManager);
    }

}
