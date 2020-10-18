package io.company.brewcraft.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantDataSourcemanagerConnectionProviderTest {

    private DataSource dataSourceMock;

    private Connection connectionMock;

    private ConnectionProvider tenantDataSourcemanagerConnectionProvider;

    @BeforeEach
    public void init() {
        dataSourceMock = mock(DataSource.class);
        connectionMock = mock(Connection.class);

        tenantDataSourcemanagerConnectionProvider = new TenantDataSourceManagerConnectionProvider(dataSourceMock);
    }

    @Test
    public void testIsUnwrappableAs_ReturnsFalse() throws Exception {
        assertFalse(tenantDataSourcemanagerConnectionProvider.isUnwrappableAs(dataSourceMock.getClass()));
    }

    @Test
    public void testUnwrap_ReturnsNull() throws Exception {
        assertNull(tenantDataSourcemanagerConnectionProvider.unwrap(dataSourceMock.getClass()));
    }
    
    @Test
    public void testGetConnection_ReturnsDataSourceConnection() throws Exception {
        when(dataSourceMock.getConnection()).thenReturn(connectionMock);
        
        assertSame(connectionMock, tenantDataSourcemanagerConnectionProvider.getConnection());
    }
    
    @Test
    public void testCloseConnection_ClosesConnection() throws Exception {
        tenantDataSourcemanagerConnectionProvider.closeConnection(connectionMock);
        
        verify(connectionMock, times(1)).close();
    }
    
    @Test
    public void testSupportsAggressiveRelease_ReturnsFalse() throws Exception {
        assertFalse(tenantDataSourcemanagerConnectionProvider.supportsAggressiveRelease());
    }
}
