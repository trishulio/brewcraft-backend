package io.company.brewcraft.migration;

import java.io.IOException;
import java.sql.SQLException;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.TenantDataSourceManager;

public class FlywayMigrationRegister implements MigrationRegister {
    private static final Logger log = LoggerFactory.getLogger(FlywayMigrationRegister.class);

    private TenantDataSourceManager dsMgr;
    private String dbScriptPathTenant;
    private String dbScriptPathAdmin;
    private FluentConfigProvider provider;

    public FlywayMigrationRegister(TenantDataSourceManager dsMgr, String dbScriptPathTenant, String dbScriptPathAdmin) {
        this(() -> Flyway.configure(), dsMgr, dbScriptPathTenant, dbScriptPathAdmin);
    }

    protected FlywayMigrationRegister(FluentConfigProvider provider, TenantDataSourceManager dsMgr, String dbScriptPathTenant, String dbScriptPathAdmin) {
        this.provider = provider;
        this.dsMgr = dsMgr;
        this.dbScriptPathTenant = dbScriptPathTenant;
        this.dbScriptPathAdmin = dbScriptPathAdmin;
    }

    @Override
    public void migrate(String tenantId) {
        try {
            Flyway fw = provider.config()
                                .locations(dbScriptPathTenant)
                                .schemas(dsMgr.fqName(tenantId))
                                .dataSource(dsMgr.getDataSource(tenantId))
                                .load();
            fw.migrate();
        } catch (SQLException | IOException e) {
            log.error("Failed to get the data-source for tenant: {}", tenantId);
            throw new RuntimeException(e);

        } catch (FlywayException e) {
            log.error("Failed to migrate tenant: {}", tenantId);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isMigrated(String tenantId) {
        try {
            Flyway fw = provider.config()
                                .locations(dbScriptPathTenant)
                                .schemas(dsMgr.fqName(tenantId))
                                .dataSource(dsMgr.getDataSource(tenantId))
                                .load();

            return fw.info().applied().length == fw.info().all().length;

        } catch (SQLException | IOException e) {
            log.error("Failed to get the data-source for tenant: {}", tenantId);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void migrate() {
        try {
            Flyway fw = provider.config()
                                .locations(dbScriptPathAdmin)
                                .schemas(dsMgr.getAdminSchemaName())
                                .dataSource(dsMgr.getAdminDataSource())
                                .load();
            fw.migrate();
        } catch (FlywayException e) {
            throw new RuntimeException(e);
        }
    }
}

interface FluentConfigProvider {
    FluentConfiguration config();
}
