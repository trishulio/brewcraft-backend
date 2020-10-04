package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.company.brewcraft.data.MultiTenantSchemaDataSource;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.TenantContext;

public class MultiTenantSchemaDataSourceTest {
    private DataSource multiDs;

    private DataSource mBaseDs;
    private Connection mConn;
    private ContextHolder mCtxHolder;
    private TenantContext mCtx;
    private TenantDataSourceManager mMgr;

    @BeforeEach
    public void init() throws SQLException {
        Map<String, String> data = new HashMap<>();
        mConn = mock(Connection.class);
        doAnswer(inv -> data.put("schema", inv.getArgument(0, String.class))).when(mConn).setSchema(anyString());
        doAnswer(inv -> data.get("schema")).when(mConn).getSchema();

        mBaseDs = mock(DataSource.class);

        mCtxHolder = mock(ContextHolder.class);
        mCtx = mock(TenantContext.class);
        doReturn(mCtx).when(mCtxHolder).getTenantContext();

        mMgr = mock(TenantDataSourceManager.class);
        doAnswer(inv -> "SCHEMA_" + inv.getArgument(0, String.class)).when(mMgr).fqName(anyString());

        multiDs = new MultiTenantSchemaDataSource(mBaseDs, mCtxHolder, mMgr);
    }

    @Test
    public void testGetConnection_ReturnsConnectionFromAdminDs() throws SQLException {
        doReturn(mConn).when(mBaseDs).getConnection();
        Connection conn = multiDs.getConnection();

        assertSame(mConn, conn);
    }

    @Test
    public void testGetConnectionUserPass_ReturnsConnectionFromAdminDs() throws SQLException {
        doReturn(mConn).when(mBaseDs).getConnection("USERNAME", "PASSWORD");

        Connection conn = multiDs.getConnection("USERNAME", "PASSWORD");
        assertSame(mConn, conn);
    }

    @Test
    public void testGetConnection_SetsConnectionSchemaToCurrentTenant() throws SQLException {
        doReturn(mConn).when(mBaseDs).getConnection();
        doReturn("TENANT_ID").when(mCtx).getTenantId();

        Connection conn = multiDs.getConnection();
        String schema = conn.getSchema();

        assertEquals("SCHEMA_TENANT_ID", schema);
    }

    @Test
    public void testGetConnectionUserPass_SetsConnectionSchemaToCurrentTenant() throws SQLException {
        doReturn(mConn).when(mBaseDs).getConnection("USERNAME", "PASSWORD");
        doReturn("TENANT_ID").when(mCtx).getTenantId();

        Connection conn = multiDs.getConnection("USERNAME", "PASSWORD");
        String schema = conn.getSchema();

        assertEquals("SCHEMA_TENANT_ID", schema);
    }

    @Test
    public void testGetParentLogger_ReturnsTheParentLoggerFromAdminDs() throws SQLFeatureNotSupportedException {
        Logger mLogger = mock(Logger.class);
        doReturn(mLogger).when(mBaseDs).getParentLogger();

        Logger logger = multiDs.getParentLogger();
        assertSame(mLogger, logger);
    }

    @Test
    public void testUnwrap_DelegatesRequestToAdminDs() throws SQLException {
        Class<Mockito> mClass = Mockito.class;
        Mockito mMockito = mock(Mockito.class);

        doReturn(mMockito).when(mBaseDs).unwrap(mClass);

        Mockito mockito = multiDs.unwrap(mClass);
        assertSame(mMockito, mockito);
    }

    @Test
    public void testIsWrapperFor_ReturnsValueFromAdminDs() throws SQLException {
        Class<Mockito> mClass = Mockito.class;

        doReturn(false).when(mBaseDs).isWrapperFor(mClass);
        assertFalse(multiDs.isWrapperFor(mClass));

        doReturn(true).when(mBaseDs).isWrapperFor(mClass);
        assertTrue(multiDs.isWrapperFor(mClass));
    }

    @Test
    public void testGetLogWriter_ReturnsLogWriterFromAdminDs() throws SQLException {
        PrintWriter mWriter = mock(PrintWriter.class);
        doReturn(mWriter).when(mBaseDs).getLogWriter();

        PrintWriter writer = multiDs.getLogWriter();
        assertSame(mWriter, writer);
    }

    @Test
    public void testSetLogWriter_DelegatesRequestToAdminDs() throws SQLException {
        PrintWriter mWriter = mock(PrintWriter.class);

        multiDs.setLogWriter(mWriter);

        verify(mBaseDs, times(1)).setLogWriter(mWriter);
    }

    @Test
    public void testSetLoginTimeout_DelegatesRequestToAdminDs() throws SQLException {
        multiDs.setLoginTimeout(1234);

        verify(mBaseDs, times(1)).setLoginTimeout(1234);
    }

    @Test
    public void testGetLoginTimeout_ReturnsLoginTimeoutFromAdminDs() throws SQLException {
        doReturn(1234).when(mBaseDs).getLoginTimeout();
        int sec = multiDs.getLoginTimeout();
        assertEquals(1234, sec);
    }
}
