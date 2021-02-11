package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

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

    public SchemaDataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder, JdbcDialect dialect, SecretsManager<String, String> secretsMgr, int poolSize) {
        this.adminDs = adminDs;
        this.cache = CacheBuilder.newBuilder().build(new CacheLoader<String, DataSource>() {
            @Override
            public DataSource load(String key) throws Exception {
                log.debug("Loading new datasource for key: {}", key);

//                verifySchemaExists(dialect, adminDs, key);
                String password = secretsMgr.get(key);

                DataSource ds = dsBuilder.clear()
                                         .copy(adminDs)
                                         .schema(key)
                                         .username(key)
                                         .password(password)
                                         .poolSize(poolSize)
                                         .build();
                return ds;
            }
        });
    }

    @Override
    public DataSource getDataSource(String id) throws SQLException, IOException {
        DataSource ds = null;
        try {
            ds = this.cache.get(id);
        } catch (ExecutionException e) {
            // TODO: These checks are buggy.
            log.error("Error loading the datasource from the cache");
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                log.error("SQLException occurred while fetching DataSource");
                throw (SQLException) cause;
            } else if (cause instanceof IOException) {
                log.error("IOException occurred while fetching DataSource");
                throw (IOException) cause;
            } else {
                log.error("Unknown error occurred while fetching DataSource");
                throw new RuntimeException(cause);
            }
        }

        return ds;
    }

    @Override
    public DataSource getAdminDataSource() {
        return this.adminDs;
    }

    private void verifySchemaExists(JdbcDialect dialect, DataSource ds, String schema) throws SQLException {
        log.debug("Verifying the schema already exists: {}", schema);

        Connection conn = ds.getConnection();
        boolean exists = dialect.schemaExists(conn, schema);
        log.debug("Schema named {} exists = {}", schema, exists);
        conn.close();

        if (!exists) {
            throw new SQLException(String.format("Invalid attempt to set a non-existent schema: %s", schema));
        }
    }
}
