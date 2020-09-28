package io.company.brewcraft.data;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.security.session.ContextHolder;

@Configuration
public class DataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JdbcDialect.class)
    public JdbcDialect jdbcDialect() {
        PostgresJdbcDialectSql sql = new PostgresJdbcDialectSql();
        return new PostgresJdbcDialect(sql);
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceManager.class)
    public DataSourceManager dataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder, JdbcDialect jdbcDialect) {
        DataSourceManager mgr = new SchemaDataSourceManager(adminDs, dsBuilder, jdbcDialect);
        return mgr;
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceBuilder.class)
    public DataSourceBuilder dsBuilder() {
        DataSourceBuilder builder = new HikariDataSourceBuilder();
        return builder;
    }

    @Bean
    @Autowired(required=false)
    @ConditionalOnMissingBean(TenantDataSourceManager.class)
    public TenantDataSourceManager tenantDsManager(ContextHolder ctxHolder, DataSourceManager dataSourceManager) {
        TenantDataSourceManager mgr = new ContextHolderDataSourceManager(ctxHolder, dataSourceManager);
        return mgr;
    }
}
