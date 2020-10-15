package io.company.brewcraft.migration;

import java.io.IOException;
import java.sql.SQLException;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.TenantDataSourceManager;

public class FlywayTenantMigrationRegister implements TenantRegister {
    public static final Logger log = LoggerFactory.getLogger(FlywayTenantMigrationRegister.class);

    private TenantDataSourceManager dsMgr;

    private String dbScriptPathTenant;
    private String dbScriptPathAdmin;

    public FlywayTenantMigrationRegister(TenantDataSourceManager dsMgr, String dbScriptPathTenant, String dbScriptPathAdmin) {
        this.dsMgr = dsMgr;
        this.dbScriptPathTenant = dbScriptPathTenant;
        this.dbScriptPathAdmin = dbScriptPathAdmin;
    }

    @Override
    public void add(String tenantId) {
        migrateTenantSchema(Flyway.configure(), tenantId);
    }

    @Override
    public void remove(String tenantId) {
        // Do nothing
    }

    @Override
    public boolean exists(String tenantId) {
        return isMigrated(Flyway.configure(), tenantId);
    }

    @Override
    public void setup() {
        migrateAppSchema(Flyway.configure());
    }

    protected void migrateAppSchema(FluentConfiguration config) {
        try {
            Flyway fw = config.locations(dbScriptPathAdmin).schemas(dsMgr.getAdminSchemaName()).dataSource(dsMgr.getAdminDataSource()).load();
            fw.migrate();
        } catch (FlywayException e) {
            throw new RuntimeException(e);
        }
    }

    protected void migrateTenantSchema(FluentConfiguration config, String tenantId) {
        try {
            Flyway fw = config.locations(dbScriptPathTenant).schemas(dsMgr.fqName(tenantId)).dataSource(dsMgr.getDataSource(tenantId)).load();
            fw.migrate();
        } catch (SQLException | IOException e) {
            log.error("Failed to get the data-source for tenant: {}", tenantId);
            throw new RuntimeException(e);

        } catch (FlywayException e) {
            log.error("Failed to migrate tenant: {}", tenantId);
            throw new RuntimeException(e);
        }
    }

    protected boolean isMigrated(FluentConfiguration config, String tenantId) {
        try {
            Flyway fw = config.locations(dbScriptPathTenant).schemas(dsMgr.fqName(tenantId)).dataSource(dsMgr.getDataSource(tenantId)).load();

            return fw.info().applied().length == fw.info().all().length;

        } catch (SQLException | IOException e) {
            log.error("Failed to get the data-source for tenant: {}", tenantId);
            throw new RuntimeException(e);
        }
    }
}
