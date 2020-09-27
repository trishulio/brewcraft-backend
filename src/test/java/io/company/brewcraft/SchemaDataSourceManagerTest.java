package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;
import io.company.brewcraft.data.SchemaDataSourceManager;
import io.company.brewcraft.security.store.KvStore;

public class SchemaDataSourceManagerTest {

    private DataSourceManager mgr;

    private DataSource mDs;
    private KvStore<String, String> mKvStore;
    private DataSourceBuilder mDsBuilder;

    @BeforeEach
    public void init() throws SQLException {
        mKvStore = mockKvStore();
        mDs = mockAdminDataSource();
        mDsBuilder = mockDsBuilder();
        mgr = new SchemaDataSourceManager(mDs, mKvStore, mDsBuilder);
    }

    @Test
    public void testGetDataSource_ReturnsDataSourceWithSpecifiedUser() throws SQLException {
        DataSource ds = mgr.getDataSource("12345");
        Connection conn = ds.getConnection();

        assertNotSame(mDs, ds);

        assertEquals("12345", conn.getSchema());
        assertFalse(conn.getAutoCommit());

        assertEquals("12345", conn.getMetaData().getUserName());
        assertEquals("jdbc:db://localhost:port/db_name", conn.getMetaData().getURL());

        verify(mDsBuilder, times(1)).clear();

        // Hack: Cannot get password from DataSource itself. Hence verifying like this.
        verify(mDsBuilder, times(1)).password("ABCDE");
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

    private KvStore<String, String> mockKvStore() {
        KvStore<String, String> store = mock(KvStore.class);
        doReturn("ABCDE").when(store).get("12345");
        return store;
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
