package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.TenantDataSourceManager;

public class FlywayTenantMigrationRegisterTest {

    public static final String DB_SCRIPT_PATH_ADMIN = "db/migrations/admin";
    public static final String DB_SCRIPT_PATH_TENANT = "db/migrations/tenant";

    private TenantRegister register;
    private TenantDataSourceManager mDsMgr;

    @BeforeEach
    public void init() {
        mDsMgr = mock(TenantDataSourceManager.class);
        // dsMgr.fqName(String id) >>> 'TENANT_' + id
        doAnswer(inv -> "TENANT_" + inv.getArgument(0, String.class)).when(mDsMgr).fqName(anyString());

        register = new FlywayTenantMigrationRegister(mDsMgr, DB_SCRIPT_PATH_TENANT, DB_SCRIPT_PATH_ADMIN);
    }

    @Test
    public void testAdd_DelegatesRequestToMigrateTenantSchemaMethodWithNewFluentConfigurationObject() {
        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);
        doNothing().when(fwRegister).migrateTenantSchema(any(FluentConfiguration.class), eq("12345"));
        fwRegister.add("12345");
        verify(fwRegister, times(1)).migrateTenantSchema(any(FluentConfiguration.class), eq("12345"));
    }

    @Test
    public void testSetup_DelegatesRequestToMigrateAppSchemaMethodWithNewFluentConfigurationObject() {
        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);
        doNothing().when(fwRegister).migrateAppSchema(any(FluentConfiguration.class));
        fwRegister.setup();
        verify(fwRegister, times(1)).migrateAppSchema(any(FluentConfiguration.class));
    }

    @Test
    public void testRemove_DoesNothing() {
        register.remove("12345");
    }

    @Test
    public void testExists_ReturnsTrue_WhenIsMigratedReturnsTrue() {
        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);
        doReturn(true).when(fwRegister).isMigrated(any(FluentConfiguration.class), eq("12345"));

        boolean b = fwRegister.exists("12345");
        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenIsMigratedReturnsTrue() {
        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);
        doReturn(false).when(fwRegister).isMigrated(any(FluentConfiguration.class), eq("12345"));

        boolean b = fwRegister.exists("12345");
        assertFalse(b);
    }

    @Test
    public void testIsMigrated_ReturnsTrue_WhenNumberOfAppliedMigrationsIsSameAsAllMigrations() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getDataSource("12345");

        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);

        FluentConfiguration mFwConfig = mock(FluentConfiguration.class);
        Flyway mFw = mockFlyway(mFwConfig, "TENANT_12345", DB_SCRIPT_PATH_TENANT, mDs);

        MigrationInfoService mInfoService = mock(MigrationInfoService.class);
        doReturn(new MigrationInfo[] { mock(MigrationInfo.class) }).when(mInfoService).all();
        doReturn(new MigrationInfo[] { mock(MigrationInfo.class) }).when(mInfoService).applied();
        doReturn(mInfoService).when(mFw).info();

        boolean b = fwRegister.isMigrated(mFwConfig, "12345");

        assertTrue(b);
    }

    @Test
    public void testIsMigrated_ReturnsFalse_WhenNumberOfAppliedMigrationsIsLessThanAllMigrations() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getDataSource("12345");

        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);

        FluentConfiguration mFwConfig = mock(FluentConfiguration.class);
        Flyway mFw = mockFlyway(mFwConfig, "TENANT_12345", DB_SCRIPT_PATH_TENANT, mDs);

        MigrationInfoService mInfoService = mock(MigrationInfoService.class);
        doReturn(new MigrationInfo[] { mock(MigrationInfo.class), mock(MigrationInfo.class) }).when(mInfoService).all();
        doReturn(new MigrationInfo[] { mock(MigrationInfo.class) }).when(mInfoService).applied();
        doReturn(mInfoService).when(mFw).info();

        boolean b = fwRegister.isMigrated(mFwConfig, "12345");

        assertFalse(b);
    }

    @Test
    public void testMigrateTenantSchema_ConfiguresFlywayWithTenantDatasourceAndMigrationScriptsAndRunsMigrate() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getDataSource("12345");

        FluentConfiguration mFwConfig = mock(FluentConfiguration.class);
        Flyway mFw = mockFlyway(mFwConfig, "TENANT_12345", DB_SCRIPT_PATH_TENANT, mDs);

        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);
        fwRegister.migrateTenantSchema(mFwConfig, "12345");

        verify(mFw, times(1)).migrate();
    }

    @Test
    public void testMigrateAppSchema_ConfiguresFlywayWithAdminDatasourceAndMigrationScriptsAndRunsMigrate() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getAdminDataSource();

        doReturn("ADMIN_SCHEMA").when(mDsMgr).getAdminSchemaName();

        FluentConfiguration mFwConfig = mock(FluentConfiguration.class);
        Flyway mFw = mockFlyway(mFwConfig, "ADMIN_SCHEMA", DB_SCRIPT_PATH_ADMIN, mDs);

        FlywayTenantMigrationRegister fwRegister = spy((FlywayTenantMigrationRegister) register);
        fwRegister.migrateAppSchema(mFwConfig);

        verify(mFw, times(1)).migrate();
    }

    private Flyway mockFlyway(FluentConfiguration config, String schemas, String location, DataSource ds) {
        Flyway mFw = mock(Flyway.class);
        doReturn(mFw).when(config).load();
        doReturn(config).when(config).locations(location);
        doReturn(config).when(config).schemas(schemas);
        doReturn(config).when(config).dataSource(ds);

        return mFw;
    }
}
