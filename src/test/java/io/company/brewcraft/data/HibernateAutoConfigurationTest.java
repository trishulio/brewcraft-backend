package io.company.brewcraft.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class HibernateAutoConfigurationTest {

    private LocalSessionFactoryBean sessionFactoryMock;

    private HibernateAutoConfiguration hibernateAutoConfiguration;

    @BeforeEach
    public void init() {
        sessionFactoryMock = mock(LocalSessionFactoryBean.class);
        hibernateAutoConfiguration = new HibernateAutoConfiguration();
    }

    @Test
    public void testMultiTenantConnectionProvider_ReturnsInstanceOfTenantConnectionProvider() {
        MultiTenantConnectionProvider multiTenantConnectionProvider = hibernateAutoConfiguration.multiTenantConnectionProvider(null);

        assertTrue(multiTenantConnectionProvider instanceof MultiTenantConnectionProvider);
    }

    @Test
    public void testCurrentTenantIdentifierResolver_ReturnsTenantIdentifierResolver() {
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver = hibernateAutoConfiguration.currentTenantIdentifierResolver(null);

        assertTrue(currentTenantIdentifierResolver instanceof TenantIdentifierResolver);
    }

    @Test
    public void testPlatformTransactionManager_ReturnsHibernateTransactionManager() {
        PlatformTransactionManager platformTransactionManager = hibernateAutoConfiguration.hibernateTransactionManager(sessionFactoryMock);

        assertTrue(platformTransactionManager instanceof HibernateTransactionManager);
    }
}
