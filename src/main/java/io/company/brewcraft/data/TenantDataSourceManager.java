package io.company.brewcraft.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface TenantDataSourceManager extends DataSourceManager {
    String getAdminSchemaName();

    String fqName(String tenantId);

    DataSource getDataSource() throws SQLException, IOException;

    Connection getConnection() throws SQLException, IOException;

    <T> T query(CheckedSupplier<T, Connection, Exception> supplier);

    <T> T query(String tenantId, CheckedSupplier<T, Connection, Exception> supplier);

    void query(CheckedRunnable<Connection, Exception> runnable);

    void query(String tenantId, CheckedRunnable<Connection, Exception> runnable);
}
