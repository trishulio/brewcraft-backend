package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.company.brewcraft.security.session.ContextHolder;

public class MultiTenantSchemaDataSource extends AbstractDelegateDataSource implements MultiSchemaDataSource {

    private ContextHolder ctxHolder;
    private TenantDataSourceManager dsMgr;

    public MultiTenantSchemaDataSource(DataSource adminDs, ContextHolder ctxHolder, TenantDataSourceManager dsMgr) {
        super(adminDs);
        this.ctxHolder = ctxHolder;
        this.dsMgr = dsMgr;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = this.adminDs.getConnection();
        setTenantSchema(conn);

        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection conn = this.adminDs.getConnection(username, password);
        setTenantSchema(conn);

        return conn;
    }

    private void setTenantSchema(Connection conn) throws SQLException {
        String tenantId = ctxHolder.getTenantContext().getTenantId();
        String schemaName = dsMgr.fqName(tenantId);
        conn.setSchema(schemaName);
    }
}
