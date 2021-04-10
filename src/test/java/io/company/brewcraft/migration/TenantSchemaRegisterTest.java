package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.company.brewcraft.data.CheckedConsumer;
import io.company.brewcraft.data.CheckedSupplier;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceManager;

@SuppressWarnings("unchecked")
public class TenantSchemaRegisterTest {
    private TenantRegister register;

    private TenantDataSourceManager mDsMgr;
    private JdbcDialect mDialect;

    @BeforeEach
    public void init() {
        mDsMgr = mock(TenantDataSourceManager.class);
        doAnswer(inv -> "TENANT_" + inv.getArgument(0, String.class)).when(mDsMgr).fqName(anyString()); // "12345" >>> "TENANT_12345"

        mDialect = mock(JdbcDialect.class);
        register = new TenantSchemaRegister(mDsMgr, mDialect);
    }

    @Test
    public void testAdd_UsesTenantDsToCreateSchema() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {inv.getArgument(1, CheckedConsumer.class).run(mConn); return null;}).when(mDsMgr).query(eq("12345"), any(CheckedConsumer.class));

        register.add("12345");

        InOrder order = inOrder(mDialect, mConn);
        order.verify(mDialect, times(1)).createSchemaIfNotExists(mConn, "TENANT_12345");

        order.verify(mConn, times(1)).commit();
    }

    @Test
    public void testExists_ReturnsTrue_WhenSchemaExistsReturnsTrue() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> inv.getArgument(1, CheckedSupplier.class).get(mConn)).when(mDsMgr).query(eq("12345"), any(CheckedSupplier.class));

        doReturn(true).when(mDialect).schemaExists(mConn, "TENANT_12345");

        boolean b = register.exists("12345");

        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenUserExistsReturnsFalse() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> inv.getArgument(1, CheckedSupplier.class).get(mConn)).when(mDsMgr).query(eq("12345"), any(CheckedSupplier.class));

        doReturn(false).when(mDialect).schemaExists(mConn, "TENANT_12345");

        boolean b = register.exists("12345");

        assertFalse(b);
    }

    @Test
    public void testRemove_DropsSchema() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {inv.getArgument(1, CheckedConsumer.class).run(mConn); return null;}).when(mDsMgr).query(eq("12345"), any(CheckedConsumer.class));

        register.remove("12345");

        InOrder order = inOrder(mDialect, mConn);
        order.verify(mDialect, times(1)).dropSchema(mConn, "TENANT_12345");

        order.verify(mConn, times(1)).commit();
    }
}
