package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.store.SecretsManager;

//TODO: All edge cases for the methods are pending where an exception is thrown at
// some point.

public class FlywayTenantRegisterTest {

    public static final String DB_SCRIPT_PATH_ADMIN = "db/migrations/admin";
    public static final String DB_SCRIPT_PATH_TENANT = "db/migrations/tenant";

    private TenantRegister register;

    private TenantDataSourceManager mDsMgr;
    private SecretsManager<String, String> mSecretMgr;
    private JdbcDialect mDialect;
    private RandomGenerator mRand;

    @BeforeEach
    public void init() {
        mDsMgr = mock(TenantDataSourceManager.class);
        // dsMgr.fqName(String id) >>> 'TENANT_' + id
        doAnswer(inv -> "TENANT_" + inv.getArgument(0, String.class)).when(mDsMgr).fqName(anyString());

        mSecretMgr = mock(SecretsManager.class);
        mDialect = mock(JdbcDialect.class);
        mRand = mock(RandomGenerator.class);

        register = new FlywayTenantRegister(mDsMgr, mSecretMgr, mDialect, mRand, DB_SCRIPT_PATH_TENANT, DB_SCRIPT_PATH_ADMIN);
    }

    @Test
    public void testRegisterUser_UsesAdminDataSourceToCreateUserAndGrantPrivilegesAndStorePassInSecretManager() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        DataSource mDs = mock(DataSource.class);
        doReturn(mConn).when(mDs).getConnection();
        doReturn(mDs).when(mDsMgr).getAdminDataSource();

        doReturn("1234567890").when(mRand).string(FlywayTenantRegister.LENGTH_PASSWORD);

        register.registerUser("12345");

        verify(mDialect, times(1)).createUser(mConn, "TENANT_12345", "1234567890");
        verify(mDialect, times(1)).grantPrivilege(mConn, "CONNECT", "DATABASE", "postgres", "TENANT_12345");
        verify(mDialect, times(1)).grantPrivilege(mConn, "CREATE", "DATABASE", "postgres", "TENANT_12345");
        verify(mSecretMgr, times(1)).put("TENANT_12345", "1234567890");
        verify(mConn, times(1)).close();
    }

    @Test
    public void testUserExists_ReturnsTrue_WhenJdbcUserExistsReturnsTrue() throws SQLException {
        Connection mConn = mock(Connection.class);
        DataSource mDs = mock(DataSource.class);
        doReturn(mConn).when(mDs).getConnection();
        doReturn(mDs).when(mDsMgr).getAdminDataSource();

        doReturn(true).when(mDialect).userExists(mConn, "TENANT_12345");

        boolean b = register.userExists("12345");

        assertTrue(b);
    }

    @Test
    public void testUserExists_ReturnsFalse_WhenJdbcUserExistsReturnsFalse() throws SQLException {
        Connection mConn = mock(Connection.class);
        DataSource mDs = mock(DataSource.class);
        doReturn(mConn).when(mDs).getConnection();
        doReturn(mDs).when(mDsMgr).getAdminDataSource();

        doReturn(false).when(mDialect).userExists(mConn, "TENANT_12345");

        boolean b = register.userExists("12345");

        assertFalse(b);
    }

    @Test
    public void testRegisterTenant_DelegatesRequestToOverloadedMethodWithNewFluentConfigurationObject() {
        ArgumentCaptor<FluentConfiguration> fwCaptor = ArgumentCaptor.forClass(FluentConfiguration.class);
        ArgumentCaptor<String> strCaptor = ArgumentCaptor.forClass(String.class);
        FlywayTenantRegister fwRegister = spy((FlywayTenantRegister) register);
        doNothing().when(fwRegister).registerTenant(fwCaptor.capture(), strCaptor.capture());

        fwRegister.registerTenant("12345");

        assertNotNull(fwCaptor.getValue());
        assertEquals("12345", strCaptor.getValue());
    }

    @Test
    public void testRegisterApp_DelegatesRequestToOverloadedMethodWithNewFluentConfigurationObject() {
        ArgumentCaptor<FluentConfiguration> fwCaptor = ArgumentCaptor.forClass(FluentConfiguration.class);
        FlywayTenantRegister fwRegister = spy((FlywayTenantRegister) register);
        doNothing().when(fwRegister).registerApp(fwCaptor.capture());

        fwRegister.registerApp();

        assertNotNull(fwCaptor.getValue());
    }

    @Test
    public void testRegisterTenant_ConfiguresFlywayWithTenantDatasourceAndMigrationScriptsAndRunsMigrate() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getDataSource("12345");

        FluentConfiguration mFwConfig = mock(FluentConfiguration.class);
        Flyway mFw = mockFlyway(mFwConfig, "TENANT_12345", DB_SCRIPT_PATH_TENANT, mDs);

        FlywayTenantRegister fwRegister = spy((FlywayTenantRegister) register);
        fwRegister.registerTenant(mFwConfig, "12345");

        verify(mFw, times(1)).migrate();
    }

    @Test
    public void testRegisterApp_ConfiguresFlywayWithAdminDatasourceAndMigrationScriptsAndRunsMigrate() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getAdminDataSource();

        doReturn("ADMIN_SCHEMA").when(mDsMgr).getAdminSchemaName();

        FluentConfiguration mFwConfig = mock(FluentConfiguration.class);
        Flyway mFw = mockFlyway(mFwConfig, "ADMIN_SCHEMA", DB_SCRIPT_PATH_ADMIN, mDs);

        FlywayTenantRegister fwRegister = spy((FlywayTenantRegister) register);
        fwRegister.registerApp(mFwConfig);

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
