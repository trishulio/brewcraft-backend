package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.company.brewcraft.security.session.ContextHolder;

public class ContextHolderTenantDataSourceManager extends AbstractTenantDsManager {

    private ContextHolder ctxHolder;

    public ContextHolderTenantDataSourceManager(ContextHolder ctxHolder, DataSourceManager dsMgr, String adminSchemaName, String schemaPrefix) {
        super(dsMgr, adminSchemaName, schemaPrefix);
        this.ctxHolder = ctxHolder;
    }

    public ContextHolderTenantDataSourceManager(ContextHolder ctxHolder, DataSourceManager dsMgr, String adminSchemaName) {
        this(ctxHolder, dsMgr, adminSchemaName, "".intern());
    }

    @Override
    public DataSource getDataSource() throws SQLException, IOException {
        return super.getDataSource(tenantId());
    }

    private String tenantId() {
        return this.ctxHolder.getPrincipalContext().getTenantId().toString();
    }
}