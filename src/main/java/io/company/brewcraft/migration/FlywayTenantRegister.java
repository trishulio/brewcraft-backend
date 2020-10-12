package io.company.brewcraft.migration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.store.SecretsManager;

public class FlywayTenantRegister implements TenantRegister {
    public static final Logger log = LoggerFactory.getLogger(FlywayTenantRegister.class);

    public static final int LENGTH_PASSWORD = 32;

    private TenantDataSourceManager dsMgr;
    private SecretsManager<String, String> secretMgr;
    private JdbcDialect dialect;
    private RandomGenerator randomGen;

    private String dbScriptPathTenant;
    private String dbScriptPathAdmin;

    public FlywayTenantRegister(TenantDataSourceManager dsMgr, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen, String dbScriptPathTenant, String dbScriptPathAdmin) {
        this.dsMgr = dsMgr;
        this.secretMgr = secretMgr;
        this.dialect = dialect;
        this.randomGen = randomGen;
        this.dbScriptPathTenant = dbScriptPathTenant;
        this.dbScriptPathAdmin = dbScriptPathAdmin;
    }

    @Override
    public void registerUser(String tenantId) {
        DataSource ds = dsMgr.getAdminDataSource();

        try (Connection conn = ds.getConnection()) {
            String fqName = dsMgr.fqName(tenantId);
            String password = randomGen.string(LENGTH_PASSWORD);

            dialect.createUser(conn, fqName, password);
            // TODO: Change the DB Name to be passed from an external source
            dialect.grantPrivilege(conn, "CONNECT", "DATABASE", "postgres", fqName);
            dialect.grantPrivilege(conn, "CREATE", "DATABASE", "postgres", fqName);

            secretMgr.put(fqName, password);
            conn.commit();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userExists(String tenantId) {
        DataSource ds = dsMgr.getAdminDataSource();
        try (Connection conn = ds.getConnection()) {
            String fqName = dsMgr.fqName(tenantId);
            return dialect.userExists(conn, fqName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerTenant(String tenantId) {
        registerTenant(Flyway.configure(), tenantId);
    }

    @Override
    public void registerApp() {
        registerApp(Flyway.configure());
    }

    @Override
    public boolean isRegisteredTenant(String tenantId) {
        throw new RuntimeException("Unimplemented Method");
    }

    @Override
    public void removeUser(String tenantId) {
        throw new RuntimeException("Unimplemented method");
    }

    @Override
    public void deregister(String tenantId) {
        throw new RuntimeException("Unimplemented method");
    }

    protected void registerApp(FluentConfiguration config) {
        try {
            Flyway fw = config.locations(dbScriptPathAdmin).schemas(dsMgr.getAdminSchemaName()).dataSource(dsMgr.getAdminDataSource()).load();
            fw.migrate();
        } catch (FlywayException e) {
            throw new RuntimeException(e);
        }
    }

    protected void registerTenant(FluentConfiguration config, String tenantId) {
        try {
            Flyway fw = config.locations(dbScriptPathTenant).schemas(dsMgr.fqName(tenantId)).dataSource(dsMgr.getDataSource(tenantId)).load();
            fw.migrate();
        } catch (FlywayException e) {
            log.error("Failed to migrate tenant: {}", tenantId);
            throw new RuntimeException(e);
        }
    }
}
