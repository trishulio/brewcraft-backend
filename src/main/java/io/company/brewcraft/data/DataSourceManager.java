package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface DataSourceManager {
    DataSource getAdminDataSource();
    DataSource getDataSource(String id) throws SQLException, IOException;
}
