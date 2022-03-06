package io.company.brewcraft.migration;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.DataSourceConfiguration;
import io.company.brewcraft.data.DataSourceConfigurationProvider;
import io.company.brewcraft.data.DataSourceQueryRunner;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceConfigurationProvider;
import io.company.brewcraft.model.Tenant;

public class TenantSchemaRegister implements TenantRegister {
    private static final Logger log = LoggerFactory.getLogger(TenantSchemaRegister.class);

    private DataSourceConfigurationProvider<UUID> configProvider;
    private DataSourceQueryRunner runner;
    private JdbcDialect dialect;

    public TenantSchemaRegister(TenantDataSourceConfigurationProvider configProvider, DataSourceQueryRunner queryRunner, JdbcDialect dialect) {
        this.configProvider = configProvider;
        this.runner = queryRunner;
        this.dialect = dialect;
    }

    @Override
    public void add(Tenant tenant) {
        DataSourceConfiguration config = configProvider.getConfiguration(tenant.getId());

        runner.query(config, conn -> {
            dialect.createSchemaIfNotExists(conn, config.getSchemaName());
            conn.commit();
        });
    }

    @Override
    public void remove(Tenant tenant) {
        DataSourceConfiguration config = configProvider.getConfiguration(tenant.getId());

        runner.query(config, conn -> {
            dialect.dropSchema(conn, config.getSchemaName());
            conn.commit();
        });
    }

    @Override
    public boolean exists(Tenant tenant) {
        DataSourceConfiguration config = configProvider.getConfiguration(tenant.getId());

        return runner.query(config, conn -> {
            return dialect.schemaExists(conn, config.getSchemaName());
        });
    }
}
