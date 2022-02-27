package io.company.brewcraft.migration;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.model.Tenant;

public class TenantSchemaRegister implements TenantRegister {
    private TenantDataSourceManager dsMgr;
    private JdbcDialect dialect;

    public TenantSchemaRegister(TenantDataSourceManager dsMgr, JdbcDialect dialect) {
        this.dsMgr = dsMgr;
        this.dialect = dialect;
    }

    @Override
    public void add(Tenant tenant) {
        String tenantId = tenant.getId().toString();

        dsMgr.query(tenantId, tenantConn -> {
            String fqName = dsMgr.fqName(tenantId);
            dialect.createSchemaIfNotExists(tenantConn, fqName);
            tenantConn.commit();
        });
    }

    @Override
    public void remove(Tenant tenant) {
        String tenantId = tenant.getId().toString();

        dsMgr.query(tenantId, tenantConn -> {
            String fqName = dsMgr.fqName(tenantId);
            dialect.dropSchema(tenantConn, fqName);
            tenantConn.commit();
        });
    }

    @Override
    public boolean exists(Tenant tenant) {
        String tenantId = tenant.getId().toString();

        return dsMgr.query(tenantId, tenantConn -> {
            String fqName = dsMgr.fqName(tenantId);
            return dialect.schemaExists(tenantConn, fqName);
        });
    }
}
