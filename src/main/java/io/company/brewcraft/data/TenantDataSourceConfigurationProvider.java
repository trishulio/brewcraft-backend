package io.company.brewcraft.data;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.security.store.SecretsManager;

public class TenantDataSourceConfigurationProvider implements DataSourceConfigurationProvider<UUID> {
    private LoadingCache<UUID, DataSourceConfiguration> cache;
    private DataSourceConfiguration adminDsConfig;

    public TenantDataSourceConfigurationProvider(DataSourceConfiguration adminDsConfig, Tenant adminTenant, GlobalDataSourceConfiguration globalTenantDsConfig, DataSourceConfigurationManager dsConfigMgr, SecretsManager<String, String> secretsManager) {
        this.adminDsConfig = adminDsConfig;

        this.cache = CacheBuilder.newBuilder()
                                 .build(new CacheLoader<UUID, DataSourceConfiguration>() {
                                    @Override
                                    public DataSourceConfiguration load(UUID tenantId) throws Exception {
                                        DataSourceConfiguration config = adminDsConfig;

                                        if (!adminTenant.getId().equals(tenantId)) {
                                            String fqName = dsConfigMgr.getFqName(globalTenantDsConfig.getSchemaPrefix(), tenantId);
                                            config = new LazyTenantDataSourceConfiguration(fqName, globalTenantDsConfig, secretsManager);
                                        }

                                        return config;
                                    }
                                 });
    }

    @Override
    public DataSourceConfiguration getConfiguration(UUID tenantId) {
        try {
            return this.cache.get(tenantId);
        } catch (ExecutionException e) {
            throw new RuntimeException(String.format("Failed to load datasource configuration for tenantId: '%s' because: %s", tenantId, e.getMessage()), e);
        }
    }

    @Override
    public DataSourceConfiguration getAdminConfiguration() {
        return this.adminDsConfig;
    }
}
