package io.company.brewcraft.data;

import java.util.Properties;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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
    public MultiTenantConnectionProvider multiTenantConnectionProvider(TenantDataSourceManager tenantDataSourceManager) {
        MultiTenantConnectionProvider multiTenantConnectionProvider = new TenantConnectionProvider(tenantDataSourceManager);
        return multiTenantConnectionProvider;
    }
    
    @Bean
    @ConditionalOnMissingBean(CurrentTenantIdentifierResolver.class)
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver(ContextHolder contextHolder) {
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver = new TenantIdentifierResolver(contextHolder);
        return currentTenantIdentifierResolver;
    }
    
    @Bean
    @ConditionalOnMissingBean(LocalSessionFactoryBean.class)
    public LocalSessionFactoryBean sessionFactory(TenantDataSourceManager tenantDataSourceManager, MultiTenantConnectionProvider multiTenantConnectionProvider, CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(tenantDataSourceManager.getAdminDataSource());
        //sessionFactory.setPackagesToScan({""});
        
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA.toString());
        hibernateProperties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

        sessionFactory.setHibernateProperties(hibernateProperties);
        sessionFactory.setMultiTenantConnectionProvider(multiTenantConnectionProvider);
        sessionFactory.setCurrentTenantIdentifierResolver(currentTenantIdentifierResolver);
        
        return sessionFactory;
    }
    
    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());
        
        return transactionManager;
    }
}
