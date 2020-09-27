package io.company.brewcraft.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface TenantDataSourceManager {

    DataSource getDataSource() throws SQLException;

    Connection getConnection() throws SQLException;
}
