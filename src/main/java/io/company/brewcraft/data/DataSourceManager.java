package io.company.brewcraft.data;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface DataSourceManager {
    DataSource getDataSource(String id) throws SQLException;
}
