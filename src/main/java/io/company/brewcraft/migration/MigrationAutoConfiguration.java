package io.company.brewcraft.migration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.store.SecretsManager;

@Configuration
public class MigrationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RandomGenerator.class)
    public RandomGenerator randomGenerator() {
        return new RandomGeneratorImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TenantRegister.class)
    public TenantRegister tenantRegister(TenantDataSourceManager dsMgr, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen, @Value("${app.config.data.migration.path.tenant}") String dbScriptPathTenant, @Value("${app.config.data.migration.path.admin}") String dbScriptPathAdmin) {
        return new FlywayTenantRegister(dsMgr, secretMgr, dialect, randomGen, dbScriptPathTenant, dbScriptPathAdmin);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationManager.class)
    public MigrationManager migrationMgr(TenantRegister tenantRegister) {
        return new FlywayMigrationManager(tenantRegister);
    }
}
