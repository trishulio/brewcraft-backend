package io.company.brewcraft.data;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

public class HibernateAutoConfigurationTest {

    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanMock;
    
    private EntityManagerFactory entityManagerFactoryMock;
    
    private JpaVendorAdapter jpaVendorAdapterMock; 
    
    private TenantDataSourceManager tenantDataSourceManageMock;
    
    private MultiTenantConnectionProvider multiTenantConnectionProviderMock;
    
    private CurrentTenantIdentifierResolver currentTenantIdentifierResolverMock;
    
    private DataSource dataSourceMock;
 
    private HibernateAutoConfiguration hibernateAutoConfiguration;

    @BeforeEach
    public void init() {
        localContainerEntityManagerFactoryBeanMock = mock(LocalContainerEntityManagerFactoryBean.class);
        entityManagerFactoryMock = mock(EntityManagerFactory.class);
        jpaVendorAdapterMock = mock(HibernateJpaVendorAdapter.class);
        tenantDataSourceManageMock = mock(ContextHolderTenantDataSourceManager.class);
        multiTenantConnectionProviderMock = mock(TenantConnectionProvider.class);
        currentTenantIdentifierResolverMock = mock(TenantIdentifierResolver.class);
        dataSourceMock = mock(DataSource.class);
      
        hibernateAutoConfiguration = new HibernateAutoConfiguration();
    }

    @Test
    public void testMultiTenantConnectionProvider_ReturnsInstanceOfTenantConnectionProvider() {
        MultiTenantConnectionProvider multiTenantConnectionProvider = hibernateAutoConfiguration.multiTenantConnectionProvider(null, null);
        
        assertTrue(multiTenantConnectionProvider instanceof TenantConnectionProvider);
    }

    @Test
    public void testCurrentTenantIdentifierResolver_ReturnsTenantIdentifierResolver() {
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver = hibernateAutoConfiguration.currentTenantIdentifierResolver(null);
        
        assertTrue(currentTenantIdentifierResolver instanceof TenantIdentifierResolver);
    }

    @Test
    public void testPlatformTransactionManager_ReturnsJpaTransactionManager() {
        when(localContainerEntityManagerFactoryBeanMock.getObject()).thenReturn(entityManagerFactoryMock);
        
        PlatformTransactionManager platformTransactionManager = hibernateAutoConfiguration.transactionManager(localContainerEntityManagerFactoryBeanMock);
        
        assertTrue(platformTransactionManager instanceof JpaTransactionManager);
    }
    
    @Test
    public void testJpaVendorAdapter_ReturnsHibernateJpaVendorAdapter() {        
        JpaVendorAdapter jpaVendorAdapter = hibernateAutoConfiguration.jpaVendorAdapter();
        
        assertTrue(jpaVendorAdapter instanceof HibernateJpaVendorAdapter);
    }
    
    @Test
    public void testLocalContainerEntityManagerFactoryBean() {    
        when(tenantDataSourceManageMock.getAdminDataSource()).thenReturn(dataSourceMock);
        
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = hibernateAutoConfiguration.entityManagerFactory(jpaVendorAdapterMock, tenantDataSourceManageMock, multiTenantConnectionProviderMock, currentTenantIdentifierResolverMock);

        assertSame(dataSourceMock, localContainerEntityManagerFactoryBean.getDataSource());
        assertSame(jpaVendorAdapterMock, localContainerEntityManagerFactoryBean.getJpaVendorAdapter());
        
        Map<String, Object> jpaPropertyMap = localContainerEntityManagerFactoryBean.getJpaPropertyMap();
        assertEquals(4, jpaPropertyMap.size());
        assertEquals(MultiTenancyStrategy.SCHEMA.toString(), jpaPropertyMap.get(Environment.MULTI_TENANT));
        assertEquals("org.hibernate.dialect.PostgreSQLDialect", jpaPropertyMap.get(Environment.DIALECT));
        assertEquals(multiTenantConnectionProviderMock, jpaPropertyMap.get(Environment.MULTI_TENANT_CONNECTION_PROVIDER));
        assertEquals(currentTenantIdentifierResolverMock, jpaPropertyMap.get(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER));
    }
}
