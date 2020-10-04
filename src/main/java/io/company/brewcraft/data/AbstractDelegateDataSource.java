package io.company.brewcraft.data;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public abstract class AbstractDelegateDataSource implements DataSource {

    protected DataSource adminDs;

    public AbstractDelegateDataSource(DataSource adminDs) {
        this.adminDs = adminDs;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.adminDs.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.adminDs.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.adminDs.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.adminDs.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.adminDs.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.adminDs.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.adminDs.getLoginTimeout();
    }
}
