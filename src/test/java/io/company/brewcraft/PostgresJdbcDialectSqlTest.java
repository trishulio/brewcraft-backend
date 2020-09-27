package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.PostgresJdbcDialectSql;

public class PostgresJdbcDialectSqlTest {

    private PostgresJdbcDialectSql pgSql;

    @BeforeEach
    public void init() {
        pgSql = new PostgresJdbcDialectSql();
    }

    @Test
    public void testCreateSchemaIfNotExists_ReturnsSqlWithSchemaName() {
        String sql = pgSql.createSchemaIfNotExist("SCHEMA_NAME");
        assertEquals("CREATE SCHEMA IF NOT EXISTS SCHEMA_NAME", sql);
    }

    @Test
    public void testCreateUser_ReturnsSqlWithUsernameAndPassword() {
        String sql = pgSql.createUser("TEST_USER", "TEST_PASS");
        assertEquals("CREATE USER TEST_USER PASSWORD TEST_PASS", sql);
    }
}
