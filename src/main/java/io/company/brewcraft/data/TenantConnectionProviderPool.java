package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class TenantConnectionProviderPool extends AbstractMultiTenantConnectionProvider {
    private static final Logger log = LoggerFactory.getLogger(TenantConnectionProviderPool.class);

    private static final long serialVersionUID = 1L;

    private LoadingCache<String, ConnectionProvider> cache;
    private String adminId;

    public TenantConnectionProviderPool(TenantDataSourceManager dsMgr, String adminId) {
        this.adminId = adminId;

        this.cache = CacheBuilder.newBuilder().build(new CacheLoader<String, ConnectionProvider>() {
            @Override
            public ConnectionProvider load(String tenantId) throws Exception {
                DataSource ds = null;
                if (tenantId.equalsIgnoreCase(adminId)) {
                    ds = dsMgr.getAdminDataSource();
                } else {
                    ds = dsMgr.getDataSource(tenantId);
                }

                return new TenantDataSourceManagerConnectionProvider(ds);
            }
        });
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return selectConnectionProvider(adminId);
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantId) {
        try {
            return this.cache.get(tenantId);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                throw new RuntimeException("Failed to fetch datasource from DataSourceManager", cause);
            } else {
                log.error("Unknown error occurred while fetching DataSource");
                throw new RuntimeException(cause);
            }
        }
    }
}
