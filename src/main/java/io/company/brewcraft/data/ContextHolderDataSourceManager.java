package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.company.brewcraft.security.session.ContextHolder;

public class ContextHolderDataSourceManager implements TenantDataSourceManager {

    private DataSourceManager connMgr;
    private ContextHolder ctxHolder;

    public ContextHolderDataSourceManager(ContextHolder ctxHolder, DataSourceManager connMgr) {
        this.connMgr = connMgr;
        this.ctxHolder = ctxHolder;
    }

    @Override
    public DataSource getDataSource() throws SQLException {
        return connMgr.getDataSource(tenantId());
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private String tenantId() {
        return this.ctxHolder.getTenantContext().getTenantId();
    }
}
