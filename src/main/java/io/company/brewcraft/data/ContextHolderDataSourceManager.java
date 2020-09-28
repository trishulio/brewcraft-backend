package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.company.brewcraft.security.session.ContextHolder;

public class ContextHolderDataSourceManager implements TenantDataSourceManager {

    private DataSourceManager dsMgr;
    private ContextHolder ctxHolder;

    public ContextHolderDataSourceManager(ContextHolder ctxHolder, DataSourceManager dsMgr) {
        this.dsMgr = dsMgr;
        this.ctxHolder = ctxHolder;
    }

    @Override
    public DataSource getDataSource() throws Exception {
        return dsMgr.getDataSource(tenantId());
    }

    @Override
    public Connection getConnection() throws Exception {
        return getDataSource().getConnection();
    }

    private String tenantId() {
        return this.ctxHolder.getTenantContext().getTenantId();
    }
}
