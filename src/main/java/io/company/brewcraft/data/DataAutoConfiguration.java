package io.company.brewcraft.data;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;

@Configuration
public class DataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JdbcDialect.class)
    public JdbcDialect jdbcDialect() {
        PostgresJdbcDialectSql sql = new PostgresJdbcDialectSql();
        return new PostgresJdbcDialect(sql);
    }

    @Bean
    @ConditionalOnMissingBean(SecretsManager.class)
    public SecretsManager<String, String> secretManager(@Value("${aws.secretsmanager.region}") String region, @Value("${aws.secretsmanager.url}") String url) {
        return new AwsSecretsManagerClient(region, url);
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceManager.class)
    public DataSourceManager dataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder, JdbcDialect jdbcDialect, SecretsManager<String, String> secretsMgr) {
        DataSourceManager mgr = new SchemaDataSourceManager(adminDs, dsBuilder, jdbcDialect, secretsMgr);
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
    public TenantDataSourceManager tenantDsManager(@Autowired(required = false) ContextHolder ctxHolder, DataSourceManager dataSourceManager, @Value("${app.config.data.schema.name.admin}") String adminSchemaName, @Value("${app.config.data.schema.prefix.tenant}") String tenantSchemaPrefix) {
        TenantDataSourceManager mgr = new ContextHolderTenantDataSourceManager(ctxHolder, dataSourceManager, adminSchemaName, tenantSchemaPrefix);
        return mgr;
    }
    
    @Bean
    @ConditionalOnMissingBean(JdbcTemplate.class)
    public JdbcTemplate jdbcTemplate(DataSourceManager dataSourceManager) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceManager.getAdminDataSource());
        return jdbcTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(TransactionTemplate.class)
    public TransactionTemplate transactionTemplate(DataSourceManager dataSourceManager) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceManager.getAdminDataSource());
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate;
    }
}
