package io.company.brewcraft.data;

import javax.sql.DataSource;

public interface DataSourceManager {
    DataSource getDataSource(String id) throws Exception;
}
