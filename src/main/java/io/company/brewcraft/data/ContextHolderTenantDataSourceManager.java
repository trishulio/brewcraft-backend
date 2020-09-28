package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.company.brewcraft.security.session.ContextHolder;

public class ContextHolderTenantDataSourceManager implements TenantDataSourceManager {

    private DataSourceManager dsMgr;
    private ContextHolder ctxHolder;
    private String schemaPrefix;
    private String adminSchemaName;

    public ContextHolderTenantDataSourceManager(ContextHolder ctxHolder, DataSourceManager dsMgr, String adminSchemaName, String schemaPrefix) {
        this.dsMgr = dsMgr;
        this.ctxHolder = ctxHolder;
        this.adminSchemaName = adminSchemaName;
        this.schemaPrefix = schemaPrefix;
    }

    public ContextHolderTenantDataSourceManager(ContextHolder ctxHolder, DataSourceManager dsMgr, String adminSchemaName) {
        this(ctxHolder, dsMgr, adminSchemaName, "".intern());
    }

    @Override
    public DataSource getDataSource() throws SQLException, IOException {
        return dsMgr.getDataSource(fqName(tenantId()));
    }

    @Override
    public Connection getConnection() throws SQLException, IOException {
        return getDataSource().getConnection();
    }

    @Override
    public DataSource getDataSource(String tenantId) throws SQLException, IOException {
        return dsMgr.getDataSource(fqName(tenantId));
    }

    @Override
    public DataSource getAdminDataSource() {
        return dsMgr.getAdminDataSource();
    }

    @Override
    public String fqName(String tenantId) {
        return String.format("%s%s", this.schemaPrefix, tenantId);
    }

    @Override
    public String getAdminSchemaName() {
        return this.adminSchemaName;
    }

    private String tenantId() {
        return this.ctxHolder.getTenantContext().getTenantId();
    }
}