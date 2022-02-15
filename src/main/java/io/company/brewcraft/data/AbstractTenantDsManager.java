package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTenantDsManager implements TenantDataSourceManager {

    private static final Logger log = LoggerFactory.getLogger(AbstractTenantDsManager.class);

    protected DataSourceManager dsMgr;
    private String schemaPrefix;
    private String adminSchemaName;

    public AbstractTenantDsManager(DataSourceManager dsMgr, String adminSchemaName, String schemaPrefix) {
        this.dsMgr = dsMgr;
        this.adminSchemaName = adminSchemaName.toLowerCase();
        this.schemaPrefix = schemaPrefix;
    }

    @Override
    public DataSource getAdminDataSource() {
        return this.dsMgr.getAdminDataSource();
    }

    @Override
    public DataSource getDataSource(String id) throws SQLException, IOException {
        return this.dsMgr.getDataSource(fqName(id));
    }

    @Override
    public String fqName(String tenantId) {
        return String.format("%s%s", this.schemaPrefix, tenantId.replace("-", "_")).toLowerCase();
    }

    @Override
    public String getAdminSchemaName() {
        return this.adminSchemaName;
    }

    @Override
    public Connection getConnection() throws SQLException, IOException {
        return getDataSource().getConnection();
    }

    @Override
    public <T> T query(String tenantId, CheckedSupplier<T, Connection, Exception> supplier) {
        try {
            DataSource ds = this.getDataSource(tenantId);
            return executeQuery(ds, supplier);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(String.format("Failed to fetch the datasource for tenant: %s", tenantId), e);
        }
    }

    @Override
    public <T> T query(CheckedSupplier<T, Connection, Exception> supplier) {
        return executeQuery(this.getAdminDataSource(), supplier);
    }

    @Override
    public void query(String tenantId, CheckedConsumer<Connection, Exception> runnable) {
        try {
            DataSource ds = this.getDataSource(tenantId);
            executeQuery(ds, runnable);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(String.format("Failed to fetch the datasource for tenant: %s", tenantId), e);
        }
    }

    @Override
    public void query(CheckedConsumer<Connection, Exception> runnable) {
        executeQuery(this.getAdminDataSource(), runnable);
    }

    private <T> T executeQuery(DataSource ds, CheckedSupplier<T, Connection, Exception> supplier) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            return supplier.get(conn);
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException eR) {
                log.error("Failed to perform rollback because {}", eR);
            }
            throw new RuntimeException("Failed to run SQL operation", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("Error occurred while attempting to close the connection", e);
                }
            }
        }
    }

    private void executeQuery(DataSource ds, CheckedConsumer<Connection, Exception> runnable) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            runnable.run(conn);
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException eR) {
                log.error("Failed to perform rollback because {}", eR);
            }
            throw new RuntimeException("Failed to run SQL operation", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("Error occurred while attempting to close the connection", e);
                }
            }
        }
    }
}
