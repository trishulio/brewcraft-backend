package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.store.SecretsManager;

public class MultiTenantSchemaDataSource extends AbstractDelegateDataSource implements MultiSchemaDataSource {

    private ContextHolder ctxHolder;
    private TenantDataSourceManager dsMgr;
    private SecretsManager<String, String> secretMgr;

    public MultiTenantSchemaDataSource(DataSource adminDs, ContextHolder ctxHolder, TenantDataSourceManager dsMgr, SecretsManager<String, String> secretMgr) {
        super(adminDs);
        this.ctxHolder = ctxHolder;
        this.dsMgr = dsMgr;
        this.secretMgr = secretMgr;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String fqName = fqName(tenantId());
        String password = getSecret(fqName);

        Connection conn = this.adminDs.getConnection(fqName, password);
        conn.setSchema(fqName);

        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection conn = this.adminDs.getConnection(username, password);
        conn.setSchema(username);

        return conn;
    }

    private String tenantId() {
        return ctxHolder.getTenantContext().getTenantId();
    }

    private String fqName(String tenantId) {
        return dsMgr.fqName(tenantId);
    }

    private String getSecret(String key) {
        try {
            return this.secretMgr.get(key);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch secret value from SecretManager", e);
        }
    }
}
