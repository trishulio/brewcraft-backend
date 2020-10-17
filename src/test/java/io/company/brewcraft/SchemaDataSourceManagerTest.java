package io.company.brewcraft;

import static io.company.brewcraft.DbMockUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.SchemaDataSourceManager;
import io.company.brewcraft.security.store.SecretsManager;

@SuppressWarnings("unchecked")
public class SchemaDataSourceManagerTest {

    private DataSourceManager mgr;

    private DataSource mDs;
    private DataSourceBuilder mDsBuilder;
    private JdbcDialect mDialect;
    private SecretsManager<String, String> mSecretsMgr;

    @BeforeEach
    public void init() throws SQLException {
        mDsBuilder = mockDsBuilder();

        mDs = mock(DataSource.class);
        createAndSetMockConnection(mDs, "USERNAME", "SCHEMA", "jdbc:db://localhost:port/db_name", false);

        mSecretsMgr = mock(SecretsManager.class);
        mDialect = mock(JdbcDialect.class);

        mgr = new SchemaDataSourceManager(mDs, mDsBuilder, mDialect, mSecretsMgr);
    }

    @Test
    public void testGetAdminDataSource_ReturnsAdminDsObject() {
        DataSource ds = mgr.getAdminDataSource();
        assertSame(mDs, ds);
    }

    @Test
    @Disabled
    // Disabled because data-source can be created without having schema. This is
    // needed in case data-source is used to create the schema)
    public void testGetDataSource_ThrowsSQLException_WhenSchemaDoesNotExists() throws SQLException, IOException {
        Connection mConn = mDs.getConnection();
        doReturn(false).when(mDialect).schemaExists(mConn, "ABC_123");
        assertThrows(SQLException.class, () -> mgr.getDataSource("ABC_123"));
    }

    @Test
    public void testGetDataSource_ReturnsDataSourceWithSpecifiedUser() throws SQLException, IOException {
        doReturn("ABCDE").when(mSecretsMgr).get("ABC_123");

        DataSource ds = mgr.getDataSource("ABC_123");
        Connection conn = ds.getConnection();

        assertNotSame(mDs, ds);

        assertEquals("ABC_123", conn.getSchema());
        assertFalse(conn.getAutoCommit());

        assertEquals("ABC_123", conn.getMetaData().getUserName());
        assertEquals("jdbc:db://localhost:port/db_name", conn.getMetaData().getURL());

        verify(mDsBuilder, times(1)).clear();

        // Hack: Cannot get password from DataSource itself. Hence verifying like this.
        assertEquals("ABCDE", mDsBuilder.password());
    }

    @Test
    public void testGetDataSource_ReturnsDataSourceFromCache_WhenSubsequentCallsAreMade() throws SQLException, IOException {
        doReturn("ABCDE").when(mSecretsMgr).get("ABC_123");

        DataSource ds1 = mgr.getDataSource("ABC_123");
        DataSource ds2 = mgr.getDataSource("ABC_123");
        DataSource ds3 = mgr.getDataSource("ABC_123");

        assertNotSame(mDs, ds1);
        assertSame(ds1, ds2);
        assertSame(ds2, ds3);

        verify(mDsBuilder, times(1)).build();
    }

    /** --------------- Mock Initialization Methods --------------- **/

    private DataSourceBuilder mockDsBuilder() throws SQLException {
        DataSourceBuilder builder = spy(new HikariDataSourceBuilder());

        DataSource ds = mock(DataSource.class);

        Connection conn = mock(Connection.class);
        doAnswer(inv -> builder.autoCommit()).when(conn).getAutoCommit();
        doAnswer(inv -> builder.schema()).when(conn).getSchema();

        DatabaseMetaData md = mock(DatabaseMetaData.class);
        doAnswer(inv -> builder.username()).when(md).getUserName();
        doAnswer(inv -> builder.url()).when(md).getURL();

        doReturn(ds).when(builder).build();
        doReturn(conn).when(ds).getConnection();
        doReturn(md).when(conn).getMetaData();

        return builder;
    }
}
