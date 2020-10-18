package io.company.brewcraft.data;

import static io.company.brewcraft.DbMockUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantConnectionProviderPoolTest {

    private static final String ADMIN_ID = "admin";

    private TenantDataSourceManager mDsMgr;
    private TenantConnectionProviderPool providerPool;

    private DataSource mAdminDs;

    @BeforeEach
    public void init() {
        mAdminDs = mock(DataSource.class);

        mDsMgr = mock(TenantDataSourceManager.class);
        providerPool = new TenantConnectionProviderPool(mDsMgr, ADMIN_ID);
    }

    @Test
    public void testGetAnyConnectionProvider_ReturnsConnectionProviderWithAdminDataSourceFromDsManager() throws SQLException {
        createAndSetMockConnection(mAdminDs, "USERNAME_1", "SCHEMA_1", "URL_1", false);
        doReturn(mAdminDs).when(mDsMgr).getAdminDataSource();

        ConnectionProvider provider = providerPool.getAnyConnectionProvider();
        Connection conn = provider.getConnection();

        assertEquals("USERNAME_1", conn.getMetaData().getUserName());
        assertEquals("SCHEMA_1", conn.getSchema());
        assertEquals(false, conn.getAutoCommit());
    }

    @Test
    public void testSelectConnectionProvider_ReturnsConnectionProviderWithAdminDs_WhenInputMatchesAdminId() throws SQLException {
        createAndSetMockConnection(mAdminDs, "USERNAME_1", "SCHEMA_1", "URL_1", false);
        doReturn(mAdminDs).when(mDsMgr).getAdminDataSource();

        ConnectionProvider provider = providerPool.selectConnectionProvider(ADMIN_ID);
        Connection conn = provider.getConnection();

        assertEquals("USERNAME_1", conn.getMetaData().getUserName());
        assertEquals("SCHEMA_1", conn.getSchema());
        assertEquals(false, conn.getAutoCommit());
    }

    @Test
    public void testSelectConnectionProvider_ReturnsConnectionProviderWithTenantDs_WhenInputIsNotAdminDs() throws SQLException, IOException {
        createAndSetMockConnection(mAdminDs, "USERNAME_1", "SCHEMA_1", "URL_1", false);
        doReturn(mAdminDs).when(mDsMgr).getDataSource("TENANT_1");

        ConnectionProvider provider = providerPool.selectConnectionProvider("TENANT_1");
        Connection conn = provider.getConnection();

        assertEquals("USERNAME_1", conn.getMetaData().getUserName());
        assertEquals("SCHEMA_1", conn.getSchema());
        assertEquals(false, conn.getAutoCommit());
    }

    @Test
    public void testSelectConnectionProvider_ReturnsCachedConnectionProvider_OnSubsequentCalls() throws SQLException, IOException {
        doReturn(mAdminDs).when(mDsMgr).getDataSource("TENANT_1");

        ConnectionProvider tenantProvider = providerPool.selectConnectionProvider("TENANT_1");
        ConnectionProvider sameTenantProvider = providerPool.selectConnectionProvider("TENANT_1");
        ConnectionProvider diffTenantProvider = providerPool.selectConnectionProvider("TENANT_2");

        assertSame(sameTenantProvider, tenantProvider);
        assertNotSame(diffTenantProvider, tenantProvider);
    }
}
