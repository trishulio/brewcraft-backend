package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import io.company.brewcraft.data.ContextHolderTenantDataSourceManager;
import io.company.brewcraft.data.DataAutoConfiguration;
import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;
import io.company.brewcraft.data.SchemaDataSourceManager;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;

public class DataAutoConfigurationTest {

    private DataAutoConfiguration config;

    @BeforeEach
    public void init() {
        config = new DataAutoConfiguration();
    }

    @Test
    public void testSecretsManager_ReturnsInstanceOfAwsSecretsManager() {
        SecretsManager<String, String> mgr = config.secretManager("url", "region");
        assertTrue(mgr instanceof AwsSecretsManagerClient);
    }

    @Test
    public void testDataSourceManager_ReturnsSchemaBasedDataSourceManager() {
        DataSourceManager mgr = config.dataSourceManager(null, null, null, null, 1);
        assertTrue(mgr instanceof SchemaDataSourceManager);
    }

    @Test
    public void testDsBuilder_ReturnsRoutingDataSourceBuilder() {
        DataSourceBuilder builder = config.dsBuilder();
        assertTrue(builder instanceof HikariDataSourceBuilder);
    }

    @Test
    public void testTenantDsManager_ReturnsContextHolderDsManager() {
        TenantDataSourceManager mgr = config.tenantDsManager(null, null, "admin_schema", "schema_prefix");
        assertTrue(mgr instanceof ContextHolderTenantDataSourceManager);
    }
    
    @Test
    public void testJdbcTemplate_ReturnsJdbcTemplate() {
        DataSourceManager dataSourceManagerMock = Mockito.mock(SchemaDataSourceManager.class);
        DataSource dataSourceMock = Mockito.mock(DataSource.class);

        Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

        JdbcTemplate jdbcTemplate = config.jdbcTemplate(dataSourceManagerMock);
    }

    @Test
    public void testTransactionTemplate_ReturnsTransactionTemplate() {
        DataSourceManager dataSourceManagerMock = Mockito.mock(SchemaDataSourceManager.class);
        DataSource dataSourceMock = Mockito.mock(DataSource.class);

        Mockito.when(dataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);

        TransactionTemplate transactionTemplate = config.transactionTemplate(dataSourceManagerMock);
    }

}
