package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

public class TenantDsManagerBasedConnProvider implements MultiTenantConnectionProvider {
    private static final long serialVersionUID = -2067410751050317257L;

    private TenantDataSourceManager dsMgr;

    public TenantDsManagerBasedConnProvider(TenantDataSourceManager dsMgr) {
        this.dsMgr = dsMgr;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        // This is DataSource implementation specific
        return null;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        DataSource ds = this.dsMgr.getAdminDataSource();
        return ds.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        try {
            DataSource ds = this.dsMgr.getDataSource(tenantId);
            return ds.getConnection();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(String.format("Failed to fetch DataSource for tenantId: %s", tenantId), e);
        }
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        // TODO: What to do with the tenantId in this case?
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }
}
