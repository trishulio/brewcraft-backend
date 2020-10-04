package io.company.brewcraft;

import static io.company.brewcraft.DbMockUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.DataSourceBuilder;
import io.company.brewcraft.data.RoutingDataSource;
import io.company.brewcraft.data.RoutingDataSourceBuilder;

public class RoutingDataSourceBuilderTest {

    private DataSourceBuilder builder;

    @BeforeEach
    public void init() {
        builder = new RoutingDataSourceBuilder();
    }

    @Test
    public void testBuild_ReturnsDataSource_CreatedWithProps() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        createAndSetMockConnection(mDs, "user_1", "schema_1", "URL", false);

        DataSource ds = builder.copy(mDs)
                               .username("test_user_2")
                               .password("test_pass_2")
                               .autoCommit(true)
                               .build();

        assertTrue(ds instanceof RoutingDataSource);

        Connection conn = ds.getConnection();
        assertEquals(true, conn.getAutoCommit()); // Uses custom autoCommit value when overridden

        DatabaseMetaData md = conn.getMetaData();
        assertEquals("test_user_2", md.getUserName()); // Uses custom username when overridden
        assertEquals("URL", md.getURL());
    }

    @Test
    public void testBuild_ThrowsError_WhenBaseDsIsNotSet() {
        try {
            builder.build();

            fail("Build should throw an exception as no base datasource was set using builder.copy()");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            assertEquals("Cannot create RoutingDataSource without a primary datasource", msg);
        }
    }

    @Test
    public void testSchema_SetsSchemaValue() {
        builder.schema("schema");
        String value = builder.schema();
        assertEquals("schema", value);
    }

    @Test
    public void testUsername_SetsUsername() {
        builder.username("username");
        String value = builder.username();
        assertEquals("username", value);
    }

    @Test
    public void testPassword_SetsPassword() {
        builder.password("password");
        String value = builder.password();
        assertEquals("password", value);
    }

    @Test
    public void testUrl_ThrowsException_BecauseItsUnsupportedForThisBuilder() {
        try {
            builder.url("url");

            fail("Set URL should throw an exception because it's unsupported for this class");
        } catch (IllegalAccessError e) {
            String msg = e.getMessage();
            assertEquals("RoutingDataSource uses base datasource's connection pool. Hence, cannot use a custom URL", msg);
        }
    }

    @Test
    public void testGetUrl_ReturnsNull_WhenBaseDsIsNotSet() {
        String url = builder.url();
        assertNull(url);
    }

    @Test
    public void testGetUrl_ReturnUrlFromBaseDsConnection_WhenBaseDsIsNotNull() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        createAndSetMockConnection(mDs, "USERNAME", "SCHEMA", "URL", false);

        builder.copy(mDs);
        String url = builder.url();

        assertEquals("URL", url);
    }

    @Test
    public void testAutoCommit_SetsAutoCommit() {
        builder.autoCommit(false);
        boolean value = builder.autoCommit();
        assertFalse(value);
    }

    @Test
    public void testClear_ClearsAllValues() {
        builder.username("username")
               .password("password")
               .schema("schema")
               .autoCommit(true)
               .clear();
        assertNull(builder.username());
        assertNull(builder.password());
        assertNull(builder.schema());
        assertFalse(builder.autoCommit());
    }

    @Test
    public void testCopy_CopiesUsernameUrlSchemaAutoCommit_FromBaseDs() throws SQLException {
        DataSource mDs = mock(DataSource.class);
        createAndSetMockConnection(mDs, "USERNAME", "SCHEMA", "URL", false);

        builder.copy(mDs);

        assertEquals("USERNAME", builder.username());
        assertEquals("URL", builder.url());
        assertEquals("SCHEMA", builder.schema());
        assertEquals(false, builder.autoCommit());

        // Close is called twice:
        // 1) builder.url()
        // 2) builder.copy()
        verify(mDs.getConnection(), times(2)).close();
    }
}
