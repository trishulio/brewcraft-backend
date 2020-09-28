package io.company.brewcraft.data;

import java.sql.Connection;

import javax.sql.DataSource;

public interface TenantDataSourceManager {

    DataSource getDataSource() throws Exception;

    Connection getConnection() throws Exception;
}
