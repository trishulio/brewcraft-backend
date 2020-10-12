package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.security.store.SecretsManager;

public class SchemaDataSourceManager implements DataSourceManager {

    private static final Logger log = LoggerFactory.getLogger(SchemaDataSourceManager.class);

    private LoadingCache<String, DataSource> cache;
    private DataSource adminDs;

    public SchemaDataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder, JdbcDialect dialect, SecretsManager<String, String> secretsMgr) {
        this.adminDs = adminDs;
        this.cache = CacheBuilder.newBuilder().build(new CacheLoader<String, DataSource>() {
            @Override
            public DataSource load(String key) throws Exception {

                key = key.toLowerCase();
                log.debug("Loading new datasource for key: {}", key);

                Connection conn = adminDs.getConnection();
                String password = secretsMgr.get(key);

                DataSource ds = dsBuilder.clear()
                                         .copy(adminDs)
                                         .schema(key)
                                         .username(key)
                                         .password(password)
                                         .build();
                conn.close();
                createSchema(dialect, ds, key);

                return ds;
            }
        });
    }

    @Override
    public DataSource getDataSource(String id) {
        DataSource ds = null;
        try {
            ds = this.cache.get(id);
        } catch (Exception e) {
            log.error("Error loading the datasource from the cache for id: {}", id);
            throw new RuntimeException(e);
        }

        return ds;
    }

    private void createSchema(JdbcDialect dialect, DataSource ds, String schema) throws SQLException {
        Connection schemaConn = ds.getConnection();
        dialect.createSchemaIfNotExists(schemaConn, schema);
        schemaConn.close();
    }

    @Override
    public DataSource getAdminDataSource() {
        return this.adminDs;
    }
}
