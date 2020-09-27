package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresJdbcDialect implements JdbcDialect {

    private PostgresJdbcDialectSql pgSql;

    public PostgresJdbcDialect(PostgresJdbcDialectSql pgSql) {
        this.pgSql = pgSql;
    }

    @Override
    public boolean createSchemaIfNotExists(Connection conn, String schemaName) throws SQLException {
        String sql = this.pgSql.createSchemaIfNotExist(schemaName);
        return executeUpdate(conn, sql) > 0;
    }

    @Override
    public void createUser(Connection conn, String username, String password) throws SQLException {
        String sql = this.pgSql.createUser(username, password);
        executeUpdate(conn, sql);
    }

    @Override
    public void grantPrivilege(Connection conn, String privilege, String resourceType, String resourceName, String username) throws SQLException {
        String sql = this.pgSql.grantPrivilege(privilege, resourceType, resourceName, username);
        executeUpdate(conn, sql);
    }

    private int executeUpdate(Connection conn, String sql) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        int updateCount = ps.executeUpdate();

        ps.close();

        return updateCount;
    }
}
