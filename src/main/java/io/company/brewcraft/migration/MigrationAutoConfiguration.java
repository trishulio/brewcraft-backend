package io.company.brewcraft.migration;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.data.DataSourceConfiguration;
import io.company.brewcraft.data.DataSourceConfigurationProvider;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.DataSourceQueryRunner;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceConfigurationProvider;
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
    @ConditionalOnMissingBean(DataSourceQueryRunner.class)
    public DataSourceQueryRunner dsQueryRunner(DataSourceManager dsManager) {
        return new DataSourceQueryRunner(dsManager);
    }

    @Bean
    @ConditionalOnMissingBean(TenantRegister.class)
    public TenantRegister tenantRegister(DataSourceQueryRunner dsQueryRunner, DataSourceConfigurationProvider<UUID> tenantDsConfigProvider, DataSourceConfiguration adminDsConfig, SecretsManager<String, String> secretMgr, JdbcDialect dialect, RandomGenerator randomGen) {
        TenantUserRegister userReg = new TenantUserRegister(dsQueryRunner, (TenantDataSourceConfigurationProvider) tenantDsConfigProvider, adminDsConfig, secretMgr, dialect, randomGen);
        TenantSchemaRegister schemaReg = new TenantSchemaRegister((TenantDataSourceConfigurationProvider) tenantDsConfigProvider, dsQueryRunner, dialect);

        return new UnifiedTenantRegister(userReg, schemaReg);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationManager.class)
    public MigrationManager migrationMgr(TenantRegister tenantRegister, MigrationRegister migrationReg) {
        return new SequentialMigrationManager(tenantRegister, migrationReg);
    }

    @Bean
    @ConditionalOnMissingBean(MigrationRegister.class)
    public MigrationRegister migrationReg(TenantDataSourceManager dsMgr, DataSourceConfigurationProvider<UUID> tenantDsConfigProvider) {
        return new FlywayTenantMigrationRegister(dsMgr, tenantDsConfigProvider);
    }
}
