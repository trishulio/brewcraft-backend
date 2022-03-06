package io.company.brewcraft.data;

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
}
