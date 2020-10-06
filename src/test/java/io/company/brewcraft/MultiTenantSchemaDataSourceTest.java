package io.company.brewcraft;

import static io.company.brewcraft.DbMockUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.company.brewcraft.data.MultiTenantSchemaDataSource;
import io.company.brewcraft.data.TenantDataSourceManager;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;
import io.company.brewcraft.security.store.SecretsManager;

public class MultiTenantSchemaDataSourceTest {
    private DataSource multiDs;

    private DataSource mBaseDs;
    private Connection mConn;
    private ContextHolder mCtxHolder;
    private PrincipalContext mCtx;
    private TenantDataSourceManager mMgr;
    private SecretsManager<String, String> mSecretMgr;

    @BeforeEach
    public void init() throws SQLException {
        mConn = mockConnection("admin_username", "admin_schema", "jdbc_url", false);

        mBaseDs = mock(DataSource.class);

        mCtxHolder = mock(ContextHolder.class);
        mCtx = mock(PrincipalContext.class);
        doReturn(mCtx).when(mCtxHolder).getPrincipalContext();

        mMgr = mock(TenantDataSourceManager.class);
        doAnswer(inv -> "SCHEMA_" + inv.getArgument(0, String.class)).when(mMgr).fqName(anyString());

        mSecretMgr = mock(SecretsManager.class);

        multiDs = new MultiTenantSchemaDataSource(mBaseDs, mCtxHolder, mMgr, mSecretMgr);
    }

    @Test
    public void testGetConnection_SetsConnectionUserPassSchemaToCurrentTenant() throws SQLException, IOException {
        doReturn("PASS_TENANT_ID").when(mSecretMgr).get("SCHEMA_TENANT_ID");

        doReturn(mConn).when(mBaseDs).getConnection("SCHEMA_TENANT_ID", "PASS_TENANT_ID");
        doReturn("TENANT_ID").when(mCtx).getTenantId();

        Connection conn = multiDs.getConnection();
        String schema = conn.getSchema();

        assertSame(mConn, conn);
        assertEquals("SCHEMA_TENANT_ID", schema);
    }

    @Test
    public void testGetConnectionUserPass_SetsConnectionUserPassSchemaToCurrentTenant() throws SQLException, IOException {
        doReturn("PASS_TENANT_ID").when(mSecretMgr).get("SCHEMA_TENANT_ID");

        doReturn(mConn).when(mBaseDs).getConnection("SCHEMA_TENANT_ID", "PASS_TENANT_ID");

        Connection conn = multiDs.getConnection("SCHEMA_TENANT_ID", "PASS_TENANT_ID");
        String schema = conn.getSchema();

        assertSame(mConn, conn);
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
