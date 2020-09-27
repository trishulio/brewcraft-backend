package io.company.brewcraft;

import static io.company.brewcraft.DbMockUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.PostgresJdbcDialect;
import io.company.brewcraft.data.PostgresJdbcDialectSql;

public class PostgresJdbcDialectTest {

    private JdbcDialect dialect;

    private PostgresJdbcDialectSql sql;

    @BeforeEach
    public void init() {
        sql = new PostgresJdbcDialectSql();
        dialect = new PostgresJdbcDialect(sql);
    }

    @Test
    public void testCreateSchemaIfNotExists_RunsCreateSchemaSqlAndReturnTrue_WhenPsReturnsCount1() throws SQLException {
        Connection mConn = mock(Connection.class);
        PreparedStatement mPs = mockPs(mConn, sql.createSchemaIfNotExist("SCHEMA_NAME"), 1);
        boolean created = dialect.createSchemaIfNotExists(mConn, "SCHEMA_NAME");

        assertTrue(created);
        verify(mPs, times(1)).close();
    }

    @Test
    public void testCreateSchemaIfNotExists_RunsCreateSchemaSqlAndReturnTrue_WhenPsReturnsCount0() throws SQLException {
        Connection mConn = mock(Connection.class);
        PreparedStatement mPs = mockPs(mConn, sql.createSchemaIfNotExist("SCHEMA_NAME"), 0);
        boolean created = dialect.createSchemaIfNotExists(mConn, "SCHEMA_NAME");

        assertFalse(created);
        verify(mPs, times(1)).close();
    }

    @Test
    public void testCreateUser_RunsCreateUserSql() throws SQLException {
        Connection mConn = mock(Connection.class);
        PreparedStatement mPs = mockPs(mConn, sql.createUser("TEST_USER", "TEST_PASS"), 1);

        dialect.createUser(mConn, "TEST_USER", "TEST_PASS");

        verify(mPs, times(1)).executeUpdate();
        verify(mPs, times(1)).close();
    }
}
