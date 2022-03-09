package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

public class TenantDataSourceManagerImpl implements TenantDataSourceManager {
    private DataSourceManager dsMgr;
    private DataSourceConfigurationProvider<UUID> dsConfigMgr;

    public TenantDataSourceManagerImpl(DataSourceManager dsMgr, TenantDataSourceConfigurationProvider dsConfigMgr) {
        this.dsMgr = dsMgr;
        this.dsConfigMgr = dsConfigMgr;
    }

    @Override
    public DataSource getDataSource(UUID tenantId) throws SQLException, IOException {
        DataSource ds = this.dsMgr.getAdminDataSource();

        if (tenantId != null) {
            DataSourceConfiguration config = this.dsConfigMgr.getConfiguration(tenantId);

            ds = this.dsMgr.getDataSource(config);
        }

        return ds;
    }
}