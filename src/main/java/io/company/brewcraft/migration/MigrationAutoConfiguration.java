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
    public TenantRegister tenantRegister(TenantDataSourceManager dsMgr, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen, @Value("spring.jpa.database") String dbName, @Value("${app.config.data.migration.path.tenant}") String dbScriptPathTenant, @Value("${app.config.data.migration.path.admin}") String dbScriptPathAdmin) {
        TenantUserRegister userReg = new TenantUserRegister(dsMgr, secretMgr, dialect, randomGen, dbName);
        TenantSchemaRegister schemaReg = new TenantSchemaRegister(dsMgr, dialect);
        FlywayTenantMigrationRegister migrationReg = new FlywayTenantMigrationRegister(dsMgr, dbScriptPathTenant, dbScriptPathAdmin);

        return new UnifiedTenantRegister(userReg, schemaReg, migrationReg);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationManager.class)
    public MigrationManager migrationMgr(TenantRegister tenantRegister) {
        return new SequentialMigrationManager(tenantRegister);
    }
}
