package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.security.store.SecretsManager;

public class SchemaDataSourceManager implements DataSourceManager {

    private static final Logger log = LoggerFactory.getLogger(SchemaDataSourceManager.class);

    private LoadingCache<String, DataSource> cache;
    
    @Value("${brewcraft.db.tenantSchemaPrefix:brewcraft_tenant_}")
    private String tenantSchemaPrefix;
    
	@Autowired
	private SecretsManager secretsManager;

    public SchemaDataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder, JdbcDialect dialect) {
        this.cache = CacheBuilder.newBuilder()
                                 .build(new CacheLoader<String, DataSource>() {
                                     @Override
                                     public DataSource load(String key) throws Exception {
                                    	 
                                         key = key.toLowerCase();
                                         log.debug("Loading new datasource for key: {}", key);

                                         Connection conn = adminDs.getConnection();
                                         String password = secretsManager.getSecret(key);

                                         DataSource ds = dsBuilder.clear()
                                                                  .url(conn.getMetaData().getURL())
                                                                  .autoCommit(conn.getAutoCommit())
                                                                  .username(key)
                                                                  .password(password)
                                                                  .schema(key)
                                                                  .build();
                                         conn.close();
                                         createSchema(dialect, ds, key);

                                         return ds;
                                     }
                                 });
    }

    @Override
    public DataSource getDataSource(String id) throws Exception {
        DataSource ds = null;
        try {
            ds = this.cache.get(tenantSchemaPrefix + id);
        } catch (Exception e) {
        	throw new Exception(e);
        }

        return ds;
    }

    private void createSchema(JdbcDialect dialect, DataSource ds, String key) throws SQLException {
        Connection schemaConn = ds.getConnection();
        dialect.createSchemaIfNotExists(schemaConn, key);
        schemaConn.close();
    }
}
