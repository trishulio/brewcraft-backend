package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.ContextHolderTenantDataSourceManager;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;

public class ContextHolderTenantDataSourceManagerTest {

    private TenantDataSourceManager mgr;
    private ContextHolder mCtxHolder;
    private DataSourceManager mConnMgr;

    @BeforeEach
    public void init() {
        mCtxHolder = mockContextHolder();
        mConnMgr = mockConnectionManager();
        mgr = new ContextHolderTenantDataSourceManager(mCtxHolder, mConnMgr, "ADMIN_SCHEMA", "TENANT_");
    }

    @Test
    public void testAdminSchemaName_ReturnsNameOfTheAdminSchema() {
        String schema = mgr.getAdminSchemaName();
        assertEquals("ADMIN_SCHEMA", schema);
    }

    @Test
    public void testSchemaName_AppendsTenantSchemaPrefixToTenantId() {
        String schema = mgr.fqName("12345");
        assertEquals("TENANT_12345", schema);
    }

    @Test
    public void testGetAdminDs_ReturnsDsManagerAdminDs() {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getAdminDataSource();

        DataSource ds = mgr.getAdminDataSource();

        assertSame(mDs, ds);
    }

    @Test
    public void testGetDataSource_ReturnsDataSource_WithTenantKeyPrefix() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("TENANT_12345");

        DataSource ds = mgr.getDataSource("12345");

        assertSame(mDs, ds);
    }

    @Test
    public void testGetConnection_ReturnsConnectionFromDataSource_WithKeyFromTenantid() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        doReturn(mDs).when(mConnMgr).getDataSource("TENANT_12345");

        Connection conn = mgr.getConnection();

        assertSame(mConn, conn);
    }

    private ContextHolder mockContextHolder() {
        PrincipalContext ctx = mock(PrincipalContext.class);
        doReturn("12345").when(ctx).getTenantId();

        ContextHolder holder = mock(ContextHolder.class);
        doReturn(ctx).when(holder).getPrincipalContext();

        return holder;
    }

    private DataSourceManager mockConnectionManager() {
        DataSourceManager mgr = mock(DataSourceManager.class);
        return mgr;
    }
}
