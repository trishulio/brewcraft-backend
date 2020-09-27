package io.company.brewcraft.data;

public class PostgresJdbcDialectSql {

    public String createSchemaIfNotExist(String schemaName) {
        return String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName);
    }

    public String createUser(String username, String password) {
        return String.format("CREATE USER %s PASSWORD '%s'", username, password);
    }

    public String grantPrivilege(String privilege, String resourceType, String resourceName, String username) {
        return String.format("GRANT %s ON %s %s TO %s", privilege, resourceType, resourceName, username);
    }
}
