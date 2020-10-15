package io.company.brewcraft.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantConnectionProviderTest {

    private TenantDataSourceManager tenantDataSourceManagerMock;

    private DataSource dataSourceMock;

    private Connection connectionMock;

    private MultiTenantConnectionProvider multiTenantConnectionProvider;

    @BeforeEach
    public void init() {
        tenantDataSourceManagerMock = mock(ContextHolderTenantDataSourceManager.class);
        dataSourceMock = mock(DataSource.class, withSettings().extraInterfaces(ConnectionProvider.class));
        connectionMock = mock(Connection.class);

        multiTenantConnectionProvider = new TenantDsManagerBasedConnProvider(tenantDataSourceManagerMock);
    }

    @Test
    public void testGetAnyConnecion() throws Exception {
        when(tenantDataSourceManagerMock.getAdminDataSource()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(connectionMock);

        Connection connection = multiTenantConnectionProvider.getAnyConnection();

        assertSame(connectionMock, connection);
    }

    @Test
    public void testGetConnection() throws Exception {
        when(tenantDataSourceManagerMock.getDataSource("testTenantId")).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(connectionMock);

        Connection connection = multiTenantConnectionProvider.getConnection("testTenantId");

        assertSame(connectionMock, connection);
    }
}
