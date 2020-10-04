package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class RoutingDataSource extends AbstractDelegateDataSource {
    private String username;
    private String password;
    private String schema;
    private boolean autoCommit;

    public RoutingDataSource(DataSource adminDs, String username, String password, String schema, boolean autoCommit) {
        super(adminDs);
        this.username = username;
        this.password = password;
        this.autoCommit = autoCommit;
        this.schema = schema;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        // TODO: Test that if admin user can get connection while not having access to
        // the schema atleast?
        Connection conn = this.adminDs.getConnection(username, password);
        conn.setSchema(schema);
        conn.setAutoCommit(autoCommit);

        return conn;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = getConnection(username, password);
        conn.setSchema(schema);
        conn.setAutoCommit(autoCommit);

        return conn;
    }
}
