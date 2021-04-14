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
import io.company.brewcraft.security.store.SecretsManager;

@SuppressWarnings("unchecked")
public class TenantUserRegisterTest {
    private TenantRegister register;

    private TenantDataSourceManager mDsMgr;
    private SecretsManager<String, String> mSecretMgr;
    private JdbcDialect mDialect;
    private RandomGenerator mRand;

    @BeforeEach
    public void init() {
        mDsMgr = mock(TenantDataSourceManager.class);
        doAnswer(inv -> "TENANT_" + inv.getArgument(0, String.class)).when(mDsMgr).fqName(anyString()); // "12345" >>> "TENANT_12345"

        mSecretMgr = mock(SecretsManager.class);
        mDialect = mock(JdbcDialect.class);
        mRand = mock(RandomGenerator.class);

        register = new TenantUserRegister(mDsMgr, mSecretMgr, mDialect, mRand, "DB_NAME");
    }

    @Test
    public void testAdd_CreatesUserAndGrantPrivilegesAndStorePassInSecretManager() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {inv.getArgument(0, CheckedConsumer.class).run(mConn); return null;}).when(mDsMgr).query(any(CheckedConsumer.class));

        doReturn("1234567890").when(mRand).string(TenantUserRegister.LENGTH_PASSWORD);

        register.add("12345");

        InOrder order = inOrder(mDialect, mConn);
        verify(mSecretMgr, times(1)).put("TENANT_12345", "1234567890");

        order.verify(mDialect, times(1)).createUser(mConn, "TENANT_12345", "1234567890");
        order.verify(mDialect, times(1)).grantPrivilege(mConn, "CONNECT", "DATABASE", "DB_NAME", "TENANT_12345");
        order.verify(mDialect, times(1)).grantPrivilege(mConn, "CREATE", "DATABASE", "DB_NAME", "TENANT_12345");

        order.verify(mConn, times(1)).commit();
    }

    @Test
    public void testExists_ReturnsTrue_WhenUserExistsReturnsTrue() throws SQLException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> inv.getArgument(0, CheckedSupplier.class).get(mConn)).when(mDsMgr).query(any(CheckedSupplier.class));

        doReturn(true).when(mDialect).userExists(mConn, "TENANT_12345");

        boolean b = register.exists("12345");

        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenUserExistsReturnsFalse() throws SQLException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> inv.getArgument(0, CheckedSupplier.class).get(mConn)).when(mDsMgr).query(any(CheckedSupplier.class));

        doReturn(false).when(mDialect).userExists(mConn, "TENANT_12345");

        boolean b = register.exists("12345");

        assertFalse(b);
    }

    @Test
    public void testRemove_TransfersUserOwnershipAndThenDropUser() throws SQLException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {inv.getArgument(0, CheckedConsumer.class).run(mConn); return null;}).when(mDsMgr).query(any(CheckedConsumer.class));

        doReturn("ADMIN_SCHEMA").when(mDsMgr).getAdminSchemaName();

        register.remove("12345");

        InOrder order = inOrder(mDialect, mConn);
        order.verify(mDialect, times(1)).reassignOwned(mConn, "TENANT_12345", "ADMIN_SCHEMA");
        order.verify(mDialect, times(1)).dropOwned(mConn, "TENANT_12345");
        order.verify(mDialect, times(1)).dropUser(mConn, "TENANT_12345");

        order.verify(mConn, times(1)).commit();
    }
}
