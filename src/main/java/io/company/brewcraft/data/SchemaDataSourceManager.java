package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.security.store.KvStore;

public class SchemaDataSourceManager implements DataSourceManager {

    private static final Logger log = LoggerFactory.getLogger(SchemaDataSourceManager.class);

    private LoadingCache<String, DataSource> cache;

    public SchemaDataSourceManager(DataSource adminDs, KvStore<String, String> credStore, DataSourceBuilder dsBuilder) {
        this.cache = CacheBuilder.newBuilder().build(new CacheLoader<String, DataSource>() {
            @Override
            public DataSource load(String key) throws Exception {
                log.debug("Loading new datasource for key: {}", key);

                Connection conn = adminDs.getConnection();
                String password = credStore.get(key);
                
                return dsBuilder.clear()
                                .url(conn.getMetaData().getURL())
                                .autoCommit(conn.getAutoCommit())
                                .username(key)
                                .password(password)
                                .schema(key)
                                .build();
            }
        });
    }

    @Override
    public DataSource getDataSource(String id) throws SQLException {
        DataSource ds = null;
        try {
            ds = this.cache.get(id);

        } catch (ExecutionException e) {
            throw new SQLException(e);
        }

        return ds;
    }
}
