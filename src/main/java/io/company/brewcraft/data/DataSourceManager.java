package io.company.brewcraft.data;

import javax.sql.DataSource;

public interface DataSourceManager {
    DataSource getAdminDataSource();
    DataSource getDataSource(String id);
}
