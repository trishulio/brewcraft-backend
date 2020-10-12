package io.company.brewcraft.data;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class TenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private static final long serialVersionUID = 1L;
    private TenantDataSourceManager tenantDataSourceManager;

    public TenantConnectionProvider(TenantDataSourceManager tenantDataSourceManager) {
        this.tenantDataSourceManager = tenantDataSourceManager;
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return (ConnectionProvider) tenantDataSourceManager.getAdminDataSource();
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {     
        return (ConnectionProvider) tenantDataSourceManager.getDataSource(tenantIdentifier);
    }
}
