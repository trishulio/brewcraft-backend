package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcDialect {

    boolean createSchemaIfNotExists(Connection conn, String schemaName) throws SQLException;

    void createUser(Connection conn, String username, String password) throws SQLException;

    void grantPrivilege(Connection conn, String privilege, String resourceType, String resourceName, String username) throws SQLException;
}
