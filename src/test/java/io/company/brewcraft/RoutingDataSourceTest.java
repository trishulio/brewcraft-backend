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

import io.company.brewcraft.data.RoutingDataSource;

public class RoutingDataSourceTest {

    private DataSource routingDs;

    private DataSource mBaseDs;
    private Connection mConn;

    @BeforeEach
    public void init() throws SQLException {
        Map<String, String> data = new HashMap<>();
        mConn = mock(Connection.class);
        doAnswer(inv -> data.put("schema", inv.getArgument(0, String.class))).when(mConn).setSchema(anyString());
        doAnswer(inv -> data.get("schema")).when(mConn).getSchema();

        mBaseDs = mock(DataSource.class);

        routingDs = new RoutingDataSource(mBaseDs, "USERNAME", "PASSWORD", "SCHEMA", false);
    }

    @Test
    public void testGetConnection_ReturnsConnectionFromAdminDs_WithCustomUsernameAndPass() throws SQLException {
        doReturn(mConn).when(mBaseDs).getConnection("USERNAME", "PASSWORD");
        Connection conn = routingDs.getConnection();
        String schema = conn.getSchema();

        assertSame(mConn, conn);
        assertEquals("SCHEMA", schema);
    }

    @Test
    public void testGetConnectionUserPass_ReturnsConnectionFromAdminDs() throws SQLException {
        doReturn(mConn).when(mBaseDs).getConnection("USERNAME_1", "PASSWORD_1");

        Connection conn = routingDs.getConnection("USERNAME_1", "PASSWORD_1");
        String schema = conn.getSchema();

        assertSame(mConn, conn);
        assertEquals("SCHEMA", schema);
    }

    @Test
    public void testGetParentLogger_ReturnsTheParentLoggerFromAdminDs() throws SQLFeatureNotSupportedException {
        Logger mLogger = mock(Logger.class);
        doReturn(mLogger).when(mBaseDs).getParentLogger();

        Logger logger = routingDs.getParentLogger();
        assertSame(mLogger, logger);
    }

    @Test
    public void testUnwrap_DelegatesRequestToAdminDs() throws SQLException {
        Class<Mockito> mClass = Mockito.class;
        Mockito mMockito = mock(Mockito.class);

        doReturn(mMockito).when(mBaseDs).unwrap(mClass);

        Mockito mockito = routingDs.unwrap(mClass);
        assertSame(mMockito, mockito);
    }

    @Test
    public void testIsWrapperFor_ReturnsValueFromAdminDs() throws SQLException {
        Class<Mockito> mClass = Mockito.class;

        doReturn(false).when(mBaseDs).isWrapperFor(mClass);
        assertFalse(routingDs.isWrapperFor(mClass));

        doReturn(true).when(mBaseDs).isWrapperFor(mClass);
        assertTrue(routingDs.isWrapperFor(mClass));
    }

    @Test
    public void testGetLogWriter_ReturnsLogWriterFromAdminDs() throws SQLException {
        PrintWriter mWriter = mock(PrintWriter.class);
        doReturn(mWriter).when(mBaseDs).getLogWriter();

        PrintWriter writer = routingDs.getLogWriter();
        assertSame(mWriter, writer);
    }

    @Test
    public void testSetLogWriter_DelegatesRequestToAdminDs() throws SQLException {
        PrintWriter mWriter = mock(PrintWriter.class);

        routingDs.setLogWriter(mWriter);

        verify(mBaseDs, times(1)).setLogWriter(mWriter);
    }

    @Test
    public void testSetLoginTimeout_DelegatesRequestToAdminDs() throws SQLException {
        routingDs.setLoginTimeout(1234);

        verify(mBaseDs, times(1)).setLoginTimeout(1234);
    }

    @Test
    public void testGetLoginTimeout_ReturnsLoginTimeoutFromAdminDs() throws SQLException {
        doReturn(1234).when(mBaseDs).getLoginTimeout();
        int sec = routingDs.getLoginTimeout();
        assertEquals(1234, sec);
    }
}
