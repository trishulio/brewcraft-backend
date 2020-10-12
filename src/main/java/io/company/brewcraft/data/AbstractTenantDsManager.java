package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class AbstractTenantDsManager implements TenantDataSourceManager {

    protected DataSourceManager dsMgr;
    private String schemaPrefix;
    private String adminSchemaName;

    public AbstractTenantDsManager(DataSourceManager dsMgr, String adminSchemaName, String schemaPrefix) {
        this.dsMgr = dsMgr;
        this.adminSchemaName = adminSchemaName;
        this.schemaPrefix = schemaPrefix;
    }

    @Override
    public DataSource getAdminDataSource() {
        return this.dsMgr.getAdminDataSource();
    }

    @Override
    public DataSource getDataSource(String id) {
        return this.dsMgr.getDataSource(fqName(id));
    }

    @Override
    public String fqName(String tenantId) {
        return String.format("%s%s", this.schemaPrefix, tenantId);
    }

    @Override
    public String getAdminSchemaName() {
        return this.adminSchemaName;
    }

    @Override
    public Connection getConnection() throws SQLException, IOException {
        return getDataSource().getConnection();
    }
}
