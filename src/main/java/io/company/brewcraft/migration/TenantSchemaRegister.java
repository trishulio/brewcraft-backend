package io.company.brewcraft.migration;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;

public class TenantSchemaRegister implements TenantRegister {
    private TenantDataSourceManager dsMgr;
    private JdbcDialect dialect;

    public TenantSchemaRegister(TenantDataSourceManager dsMgr, JdbcDialect dialect) {
        this.dsMgr = dsMgr;
        this.dialect = dialect;
    }

    @Override
    public void add(String tenantId) {
        dsMgr.query(tenantId, tenantConn -> {
            String fqName = dsMgr.fqName(tenantId);
            dialect.createSchemaIfNotExists(tenantConn, fqName);
            tenantConn.commit();
            return null;
        });
    }

    @Override
    public void remove(String tenantId) {
        dsMgr.query(tenantId, tenantConn -> {
            String fqName = dsMgr.fqName(tenantId);
            dialect.dropSchema(tenantConn, fqName);
            tenantConn.commit();
            return null;
        });
    }

    @Override
    public boolean exists(String tenantId) {
        return dsMgr.query(tenantId, tenantConn -> {
            String fqName = dsMgr.fqName(tenantId);
            return dialect.schemaExists(tenantConn, fqName);
        });
    }

    @Override
    public void setup() {
        // Does nothing
    }
}
