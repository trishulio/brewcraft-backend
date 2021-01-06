package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceBuilder extends AbstractDataSourceBuilder {
    private static final Logger log = LoggerFactory.getLogger(HikariDataSourceBuilder.class);

    public static final String KEY_USERNAME = "dataSource.user";
    public static final String KEY_PASSWORD = "dataSource.password";
    public static final String KEY_URL = "jdbcUrl";
    public static final String KEY_AUTO_COMMIT = "autoCommit";
    public static final String KEY_SCHEMA = "schema";
    public static final String POOL_SIZE = "maximumPoolSize";

    public HikariDataSourceBuilder() {
        super(KEY_USERNAME, KEY_PASSWORD, KEY_URL, KEY_AUTO_COMMIT, KEY_SCHEMA, POOL_SIZE);
    }

    @Override
    public DataSource build() {
        HikariConfig config = new HikariConfig(props);
        DataSource ds = new HikariDataSource(config);

        return ds;
    }

    @Override
    public DataSourceBuilder copy(DataSource ds) {
        try (Connection conn = ds.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            username(md.getUserName());
            url(md.getURL());
            schema(conn.getSchema());
            autoCommit(conn.getAutoCommit());

            log.debug("poolSize not copied because it's not publicly available by JDBC");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to copy datasource metadata", e);
        }

        return this;
    }
}
