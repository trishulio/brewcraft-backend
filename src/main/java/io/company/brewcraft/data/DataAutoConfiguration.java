package io.company.brewcraft.data;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.store.KvStore;

@Configuration
public class DataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DataSourceManager.class)
    public DataSourceManager dataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder) {
        // TODO: Implement a Vault-based KvStore
        KvStore<String, String> credsStore = null;
        DataSourceManager mgr = new SchemaDataSourceManager(adminDs, credsStore, dsBuilder);

        return mgr;
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceBuilder.class)
    public DataSourceBuilder dsBuilder() {
        DataSourceBuilder builder = new HikariDataSourceBuilder();
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean(TenantDataSourceManager.class)
    public TenantDataSourceManager tenantDsManager(ContextHolder ctxHolder, DataSourceManager dataSourceManager) {
        TenantDataSourceManager mgr = new ContextHolderDataSourceManager(ctxHolder, dataSourceManager);
        return mgr;
    }
}
