package io.company.brewcraft.migration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.store.SecretsManager;
import io.company.brewcraft.service.IdpUserRepository;

@Configuration
public class MigrationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RandomGenerator.class)
    public RandomGenerator randomGenerator() {
        return new RandomGeneratorImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TenantRegister.class)
    public TenantRegister tenantRegister(TenantDataSourceManager dsMgr, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen, @Value("${spring.datasource.dbname}") String dbName) {
        TenantUserRegister userReg = new TenantUserRegister(dsMgr, secretMgr, dialect, randomGen, dbName);
        TenantSchemaRegister schemaReg = new TenantSchemaRegister(dsMgr, dialect);

        return new UnifiedTenantRegister(userReg, schemaReg);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationManager.class)
    public MigrationManager migrationMgr(TenantRegister tenantRegister, MigrationRegister migrationReg, IdpUserRepository idpUserRepository) {
        return new SequentialMigrationManager(tenantRegister, migrationReg, idpUserRepository);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationRegister.class)
    public MigrationRegister migrationReg(TenantDataSourceManager dsMgr, @Value("${app.config.data.migration.path.tenant}") String dbScriptPathTenant, @Value("${app.config.data.migration.path.admin}") String dbScriptPathAdmin) {
        return new FlywayMigrationRegister(dsMgr, dbScriptPathTenant, dbScriptPathAdmin);
    }

}
