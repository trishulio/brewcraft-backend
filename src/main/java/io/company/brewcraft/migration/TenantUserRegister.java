package io.company.brewcraft.migration;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.security.store.SecretsManager;

public class TenantUserRegister implements TenantRegister {
    private static final Logger log = LoggerFactory.getLogger(TenantUserRegister.class);

    public static final int LENGTH_PASSWORD = 32;

    private TenantDataSourceManager dsMgr;
    private SecretsManager<String, String> secretMgr;
    private JdbcDialect dialect;
    private RandomGenerator randomGen;
    private String dbName;

    public TenantUserRegister(TenantDataSourceManager dsMgr, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen, String dbName) {
        this.dsMgr = dsMgr;
        this.secretMgr = secretMgr;
        this.dialect = dialect;
        this.randomGen = randomGen;
        this.dbName = dbName;
    }

    @Override
    public void add(Tenant tenant) {
        String tenantId = tenant.getId().toString();

        dsMgr.query(conn -> {
            String fqName = dsMgr.fqName(tenantId);
            String password = randomGen.string(LENGTH_PASSWORD);

            dialect.createUser(conn, fqName, password);
            dialect.grantPrivilege(conn, "CONNECT", "DATABASE", this.dbName, fqName);
            dialect.grantPrivilege(conn, "CREATE", "DATABASE", this.dbName, fqName);

            secretMgr.put(fqName, password);
            conn.commit();
        });
    }

    @Override
    public void remove(Tenant tenant) {
        String tenantId = tenant.getId().toString();

        dsMgr.query(conn -> {
            String fqName = dsMgr.fqName(tenantId);
            dialect.reassignOwned(conn, fqName, dsMgr.getAdminSchemaName());
            dialect.dropOwned(conn, fqName);
            dialect.dropUser(conn, fqName);
            conn.commit();
        });
    }

    @Override
    public boolean exists(Tenant tenant) {
        String tenantId = tenant.getId().toString();

        return dsMgr.query((Connection conn) -> dialect.userExists(conn, dsMgr.fqName(tenantId)));
    }
}
