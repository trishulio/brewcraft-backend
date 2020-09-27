package io.company.brewcraft.data;

import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceBuilder implements DataSourceBuilder {

    public static final String KEY_USERNAME = "dataSource.user";
    public static final String KEY_PASSWORD = "dataSource.password";
    public static final String KEY_URL = "jdbcUrl";
    public static final String KEY_SCHEMA = "schema";
    public static final String KEY_AUTO_COMMIT = "autoCommit";

    private Properties props;

    public HikariDataSourceBuilder() {
        this.props = new Properties(5);
    }

    @Override
    public DataSource build() {
        HikariConfig config = new HikariConfig(props);
        DataSource ds = new HikariDataSource(config);

        return ds;
    }

    @Override
    public DataSourceBuilder username(String username) {
        this.props.setProperty(KEY_USERNAME, username);
        return this;
    }

    @Override
    public DataSourceBuilder password(String password) {
        props.setProperty(KEY_PASSWORD, password);
        return this;
    }

    @Override
    public DataSourceBuilder url(String url) {
        props.setProperty(KEY_URL, url);
        return this;
    }

    @Override
    public DataSourceBuilder schema(String schema) {
        props.setProperty(KEY_SCHEMA, schema);
        return this;
    }

    @Override
    public DataSourceBuilder autoCommit(boolean autoCommit) {
        props.put(KEY_AUTO_COMMIT, autoCommit);
        return this;
    }

    @Override
    public String username() {
        return props.getProperty(KEY_USERNAME);
    }

    @Override
    public String password() {
        return props.getProperty(KEY_PASSWORD);
    }

    @Override
    public String url() {
        return props.getProperty(KEY_URL);
    }

    @Override
    public String schema() {
        return props.getProperty(KEY_SCHEMA);
    }

    @Override
    public boolean autoCommit() {
        boolean b = false;
        Object o = props.get(KEY_AUTO_COMMIT);
        if (o != null) {
            b = (Boolean) o;
        }

        return b;
    }

    @Override
    public DataSourceBuilder clear() {
        this.props.clear();
        return this;
    }
}
