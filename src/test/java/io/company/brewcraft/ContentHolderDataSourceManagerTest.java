package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.ContextHolderDataSourceManager;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.TenantContext;

public class ContentHolderDataSourceManagerTest {

    private TenantDataSourceManager mgr;
    private ContextHolder mCtxHolder;
    private DataSourceManager mConnMgr;

    @BeforeEach
    public void init() {
        mCtxHolder = mockContextHolder();
        mConnMgr = mockConnectionManager();
        mgr = new ContextHolderDataSourceManager(mCtxHolder, mConnMgr);
    }

    @Test
    public void testGetDataSource_ReturnsDataSource_WithKeyFromTenantId() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mConnMgr).getDataSource("12345");

        DataSource ds = mgr.getDataSource();

        assertSame(mDs, ds);
    }

    @Test
    public void testGetConnection_ReturnsConnectionFromDataSource_WithKeyFromTenantid() throws SQLException {
        DataSource mDs = mock(DataSource.class);

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        doReturn(mDs).when(mConnMgr).getDataSource("12345");

        Connection conn = mgr.getConnection();

        assertSame(mConn, conn);
    }

    private ContextHolder mockContextHolder() {
        TenantContext ctx = mock(TenantContext.class);
        doReturn("12345").when(ctx).getTenantId();

        ContextHolder holder = mock(ContextHolder.class);
        doReturn(ctx).when(holder).getTenantContext();

        return holder;
    }

    private DataSourceManager mockConnectionManager() {
        DataSourceManager mgr = mock(DataSourceManager.class);
        return mgr;
    }
}
