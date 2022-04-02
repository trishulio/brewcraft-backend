package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.CachingDataSourceManager;
import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.DataSourceConfiguration;
import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.HikariDataSourceBuilder;

@SuppressWarnings("unchecked")
public class SchemaDataSourceManagerTest {
    public static final int POOL_SIZE = 123;

    private DataSourceManager mgr;

    private DataSource mAdminDs;
    private DataSourceBuilder mDsBuilder;

    @BeforeEach
    public void init() throws SQLException {
        mDsBuilder = mockDsBuilder();

        mAdminDs = mock(DataSource.class);

        mgr = new CachingDataSourceManager(mAdminDs, mDsBuilder);
    }

    @Test
    public void testGetDataSource_ReturnsNewDataSourceFromConfig() throws SQLException, IOException, URISyntaxException {
        DataSourceConfiguration mConfig = mock(DataSourceConfiguration.class);
        doReturn(new URI("jdbc://localhost:5432")).when(mConfig).getUrl();
        doReturn("schema").when(mConfig).getSchemaName();
        doReturn("username").when(mConfig).getUserName();
        doReturn("password").when(mConfig).getPassword();
        doReturn(1).when(mConfig).getPoolSize();
        doReturn(true).when(mConfig).isAutoCommit();

        DataSource ds = mgr.getDataSource(mConfig);
        ds = mgr.getDataSource(mConfig);
        ds = mgr.getDataSource(mConfig);

        assertEquals("jdbc://localhost:5432", ds.getConnection().getMetaData().getURL());
        assertEquals("schema", ds.getConnection().getSchema());
        assertEquals("username", ds.getConnection().getMetaData().getUserName());
        assertEquals(true, ds.getConnection().getAutoCommit());

        verify(mDsBuilder, times(1)).poolSize(1);
        verify(mDsBuilder, times(1)).password("password");

        verify(mDsBuilder, times(1)).build();
    }

    @Test
    public void testGetAdminDataSource_ReturnsAdminDs() {
        assertEquals(mAdminDs, mgr.getAdminDataSource());
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
