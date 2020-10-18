package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.company.brewcraft.data.CheckedRunnable;
import io.company.brewcraft.data.CheckedSupplier;
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
    public void testAdminSchemaName_ReturnsNameOfTheAdminSchemaInLowerCase() {
        String schema = mgr.getAdminSchemaName();
        assertEquals("admin_schema", schema);
    }

    @Test
    public void testSchemaName_AppendsTenantSchemaPrefixToTenantIdInLowerCase() {
        String schema = mgr.fqName("12345");
        assertEquals("tenant_12345", schema);
    }

    @Test
    public void testGetAdminDs_ReturnsDsManagerAdminDs() {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getAdminDataSource();

        DataSource ds = mgr.getAdminDataSource();

        assertSame(mDs, ds);
    }

    @Test
    public void testGetDataSource_ReturnsDataSource_WithTenantKeyPrefixInLowerCase() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("tenant_12345");

        DataSource ds = mgr.getDataSource("12345");

        assertSame(mDs, ds);
    }

    @Test
    public void testGetConnection_ReturnsConnectionFromDataSource_WithKeyFromTenantIdInLowerCase() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        doReturn(mDs).when(mConnMgr).getDataSource("tenant_12345");

        Connection conn = mgr.getConnection();

        assertSame(mConn, conn);
    }

    @Test
    public void testQuery_CallsSupplierAndReturnsResponse_WithAdminDs() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getAdminDataSource();

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        int res = mgr.query(new CheckedSupplier<Integer, Connection, Exception>() {
            @Override
            public Integer get(Connection conn) throws Exception {
                assertSame(mConn, conn);
                return 1;
            }
        });

        assertEquals(1, res);
        verify(mConn, times(1)).close();
        verify(mConn, times(0)).rollback();
    }

    @Test
    public void testQuery_PerformsConnectionRollback_WhenErrorIsThrownInSupplier() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getAdminDataSource();
        
        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        assertThrows(RuntimeException.class, () -> {
            mgr.query(new CheckedSupplier<Integer, Connection, Exception>() {
                @Override
                public Integer get(Connection conn) throws Exception {
                    throw new SQLException("Should result in rollback on Connection");
                }
            });
        });

        InOrder order = inOrder(mConn);
        order.verify(mConn, times(1)).rollback();
        order.verify(mConn, times(1)).close();
    }

    @Test
    public void testQueryWithTenantId_CallsSupplier_WithTenantDs() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("tenant_12345");

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        int res = mgr.query("12345", new CheckedSupplier<Integer, Connection, Exception>() {
            @Override
            public Integer get(Connection conn) throws Exception {
                assertSame(mConn, conn);
                return 1;
            }
        });

        assertEquals(1, res);
        verify(mConn, times(1)).close();
        verify(mConn, times(0)).rollback();
    }

    @Test
    public void testQueryWithTenantId_PerformsConnectionRollback_WhenErrorIsThrownInSupplier() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("tenant_12345");

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        assertThrows(RuntimeException.class, () -> {
            mgr.query("12345", new CheckedSupplier<Integer, Connection, Exception>() {
                @Override
                public Integer get(Connection conn) throws Exception {
                    throw new SQLException("Should result in rollback on Connection");
                }
            });
        });

        InOrder order = inOrder(mConn);
        order.verify(mConn, times(1)).rollback();
        order.verify(mConn, times(1)).close();
    }

    @Test
    public void testQuery_CallsRunnable_WithAdminDs() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getAdminDataSource();

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        mgr.query(new CheckedRunnable<Connection, Exception>() {
            @Override
            public void run(Connection conn) throws Exception {
                assertSame(mConn, conn);
            }
        });

        verify(mConn, times(1)).close();
        verify(mConn, times(0)).rollback();
    }

    @Test
    public void testQuery_PerformsConnectionRollback_WhenErrorIsThrownInRunnable() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getAdminDataSource();

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        assertThrows(RuntimeException.class, () -> {
            mgr.query(new CheckedRunnable<Connection, Exception>() {
                @Override
                public void run(Connection conn) throws Exception {
                    throw new SQLException("Should result in rollback on Connection");
                }
            });
        });

        InOrder order = inOrder(mConn);
        order.verify(mConn, times(1)).rollback();
        order.verify(mConn, times(1)).close();
    }

    @Test
    public void testQueryWithTenantId_CallsRunnable_WithTenantDs() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("tenant_12345");

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        mgr.query("12345", new CheckedRunnable<Connection, Exception>() {
            @Override
            public void run(Connection conn) throws Exception {
                assertSame(mConn, conn);
            }
        });

        verify(mConn, times(1)).close();
        verify(mConn, times(0)).rollback();
    }

    @Test
    public void testQueryWithTenantId_PerformsConnectionRollback_WhenErrorIsThrown() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("tenant_12345");

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        assertThrows(RuntimeException.class, () -> {
            mgr.query("12345", new CheckedRunnable<Connection, Exception>() {
                @Override
                public void run(Connection conn) throws Exception {
                    throw new SQLException();
                }
            });
        });

        InOrder order = inOrder(mConn);
        order.verify(mConn, times(1)).rollback();
        order.verify(mConn, times(1)).close();
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
