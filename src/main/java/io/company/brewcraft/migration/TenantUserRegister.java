package io.company.brewcraft.migration;

import java.sql.Connection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.DataSourceConfiguration;
import io.company.brewcraft.data.DataSourceConfigurationProvider;
import io.company.brewcraft.data.DataSourceQueryRunner;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceConfigurationProvider;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.security.store.SecretsManager;

public class TenantUserRegister implements TenantRegister {
    private static final Logger log = LoggerFactory.getLogger(TenantUserRegister.class);

    public static final int PASSWORD_LENGTH = 48;

    private DataSourceQueryRunner runner;
    private DataSourceConfigurationProvider<UUID> configMgr;
    private DataSourceConfiguration adminDsConfig;
    private SecretsManager<String, String> secretMgr;
    private JdbcDialect dialect;
    private RandomGenerator randGen;

    public TenantUserRegister(DataSourceQueryRunner dsQueryRunner, TenantDataSourceConfigurationProvider tenantDsConfigProvider, DataSourceConfiguration adminDsConfig, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randGen) {
        this.runner = dsQueryRunner;
        this.configMgr = tenantDsConfigProvider;
        this.adminDsConfig = adminDsConfig;
        this.secretMgr = secretMgr;
        this.dialect = dialect;
        this.randGen = randGen;
    }

    @Override
    public void add(Tenant tenant) {
        DataSourceConfiguration config = this.configMgr.getConfiguration(tenant.getId());

        runner.query(conn -> {
            String password = this.randGen.string(PASSWORD_LENGTH);

            dialect.createUser(conn, config.getUserName(), password);
            dialect.grantPrivilege(conn, "CONNECT", "DATABASE", config.getDbName(), config.getUserName());
            dialect.grantPrivilege(conn, "CREATE", "DATABASE", config.getDbName(), config.getUserName());

            // Note: The reason schemaName is used instead of the tenant-id (as convention)
            // is because fully-qualified schema-name is unique per tenant. Since we're
            // using a global app-wide secret's manager that may store other secrets, there
            // may be another secret under the tenant-id key that we may override.
            // If the key here changes, also change the retrieval logic in the
            // LazyTenantDataSourceConfiguration's getPassword method.
            secretMgr.put(config.getSchemaName(), password);
            conn.commit();
        });
    }

    @Override
    public void put(Tenant tenant) {
        if (!exists(tenant)) {
            add(tenant);
        }
    }

    @Override
    public void remove(Tenant tenant) {
        DataSourceConfiguration config = this.configMgr.getConfiguration(tenant.getId());

        runner.query(conn -> {
            dialect.reassignOwnedByTo(conn, config.getUserName(), adminDsConfig.getUserName());
            dialect.dropOwnedBy(conn, config.getUserName());
            dialect.dropUser(conn, config.getUserName());
            conn.commit();
        });
    }

    @Override
    public boolean exists(Tenant tenant) {
        DataSourceConfiguration config = this.configMgr.getConfiguration(tenant.getId());

        return runner.query((Connection conn) -> dialect.userExists(conn, config.getUserName()));
    }
}
