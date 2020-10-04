package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class RoutingDataSourceBuilder extends AbstractDataSourceBuilder {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SCHEMA = "schema";
    public static final String KEY_URL = null; // Unsupported property for this builder
    public static final String KEY_AUTO_COMMIT = "autocommit";

    private DataSource ds;

    public RoutingDataSourceBuilder() {
        super(KEY_USERNAME, KEY_PASSWORD, KEY_URL, KEY_AUTO_COMMIT, KEY_SCHEMA);
    }

    @Override
    public DataSource build() {
        if (ds == null) {
            throw new RuntimeException("Cannot create RoutingDataSource without a primary datasource");
        }
        DataSource routingDs = new RoutingDataSource(ds, username(), password(), schema(), autoCommit());

        return routingDs;
    }

    @Override
    public DataSourceBuilder url(String url) {
        throw new IllegalAccessError("RoutingDataSource uses base datasource's connection pool. Hence, cannot use a custom URL");
    }

    @Override
    public String url() {
        String url = null;
        if (ds != null) {
            try (Connection conn = ds.getConnection()) {
                url = conn.getMetaData().getURL();
            } catch (SQLException e) {
                throw new RuntimeException("Error while accessing dataSource", e);
            }
        }
        return url;
    }

    @Override
    public DataSourceBuilder copy(DataSource ds) {
        this.ds = ds;

        try (Connection conn = ds.getConnection()) {
            username(conn.getMetaData().getUserName());
            schema(conn.getSchema());
            autoCommit(conn.getAutoCommit());

        } catch (SQLException e) {
            throw new RuntimeException("Failed to copy datasource metadata", e);
        }

        return this;
    }
}
