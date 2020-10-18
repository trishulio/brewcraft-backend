package io.company.brewcraft.data;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.company.brewcraft.security.auth.AuthConfiguration;
import io.company.brewcraft.security.session.ContextHolder;

@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class, AuthConfiguration.class})
@EnableTransactionManagement
public class HibernateAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(MultiTenantConnectionProvider.class)
    public MultiTenantConnectionProvider multiTenantConnectionProvider(TenantDataSourceManager tenantDataSourceManager, @Value("${app.config.data.admin.name}") String adminIdentifier) {
        MultiTenantConnectionProvider multiTenantConnectionProvider = new TenantConnectionProviderPool(tenantDataSourceManager, adminIdentifier);
        return multiTenantConnectionProvider;
    }
    
    @Bean
    @ConditionalOnMissingBean(CurrentTenantIdentifierResolver.class)
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver(ContextHolder contextHolder) {
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver = new TenantIdentifierResolver(contextHolder);
        return currentTenantIdentifierResolver;
    }
    
    @Bean
    @ConditionalOnMissingBean(JpaVendorAdapter.class)
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
    
    @Bean
    @ConditionalOnMissingBean(LocalContainerEntityManagerFactoryBean.class)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter, TenantDataSourceManager tenantDataSourceManager, MultiTenantConnectionProvider multiTenantConnectionProvider, CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(tenantDataSourceManager.getAdminDataSource());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPackagesToScan("io.company.brewcraft.model");
        
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA.toString());
        jpaProperties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        jpaProperties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        entityManagerFactory.setJpaPropertyMap(jpaProperties);
        return entityManagerFactory;
    }
    
    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        PlatformTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory.getObject());
        
        return transactionManager;
    }
}
