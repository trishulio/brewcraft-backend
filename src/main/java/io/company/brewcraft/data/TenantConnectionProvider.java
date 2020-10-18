package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class TenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private static final long serialVersionUID = 1L;
    private TenantDataSourceManager tenantDataSourceManager;
    private final String adminIdentifier;

    public TenantConnectionProvider(TenantDataSourceManager tenantDataSourceManager, String adminIdentifier) {
        this.tenantDataSourceManager = tenantDataSourceManager;
        this.adminIdentifier = adminIdentifier;
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        ConnectionProvider connectionProvider = new TenantDataSourceManagerConnectionProvider(tenantDataSourceManager.getAdminDataSource());
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        ConnectionProvider connectionProvider = null;

        if (adminIdentifier.equals(tenantIdentifier)) {
            connectionProvider = getAnyConnectionProvider();
        } else {
            try {
                connectionProvider = new TenantDataSourceManagerConnectionProvider(tenantDataSourceManager.getDataSource(tenantIdentifier));
            } catch (SQLException | IOException e) {
                throw new RuntimeException(String.format("Failed to fetch DataSource for tenantId: %s", tenantIdentifier), e);
            }
        }

        return connectionProvider;
    }
}
