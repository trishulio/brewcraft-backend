package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.SchemaDataSourceManager;
import io.company.brewcraft.security.store.SecretsManager;

public class SchemaDataSourceManagerTest {

    private DataSourceManager mgr;

    private DataSource mDs;
    private DataSourceBuilder mDsBuilder;
    private JdbcDialect dialect;
    private SecretsManager<String, String> mSecretsMgr;

    @BeforeEach
    public void init() throws SQLException {
        mDs = mockAdminDataSource();
        mDsBuilder = mockDsBuilder();
        mSecretsMgr = mock(SecretsManager.class);
        dialect = mock(JdbcDialect.class);
        mgr = new SchemaDataSourceManager(mDs, mDsBuilder, dialect, mSecretsMgr);
    }

    @Test
    public void testGetAdminDataSource_ReturnsAdminDsObject() {
        DataSource ds = mgr.getAdminDataSource();
        assertSame(mDs, ds);
    }

    @Test
    public void testGetDataSource_ReturnsDataSourceWithSpecifiedUserInLowerCase() throws SQLException, IOException {
        doReturn("ABCDE").when(mSecretsMgr).get("abc_123");
        DataSource ds = mgr.getDataSource("ABC_123");
        Connection conn = ds.getConnection();

        assertNotSame(mDs, ds);

        assertEquals("abc_123", conn.getSchema());
        assertFalse(conn.getAutoCommit());

        assertEquals("abc_123", conn.getMetaData().getUserName());
        assertEquals("jdbc:db://localhost:port/db_name", conn.getMetaData().getURL());

        verify(mDsBuilder, times(1)).clear();
        verify(conn, times(1)).close();

        verify(dialect, times(1)).createSchemaIfNotExists(conn, "abc_123");

        // Hack: Cannot get password from DataSource itself. Hence verifying like this.
        assertEquals("ABCDE", mDsBuilder.password());
    }

    /** --------------- Mock Initialization Methods --------------- **/

    private DataSource mockAdminDataSource() throws SQLException {
        DatabaseMetaData metadata = mock(DatabaseMetaData.class);
        doReturn("jdbc:db://localhost:port/db_name").when(metadata).getURL();

        Connection conn = mock(Connection.class);
        doReturn(metadata).when(conn).getMetaData();
        doReturn(false).when(conn).getAutoCommit();

        DataSource ds = mock(DataSource.class);
        doReturn(conn).when(ds).getConnection();

        return ds;
    }

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
