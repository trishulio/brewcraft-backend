package io.company.brewcraft.data;

import javax.sql.DataSource;

public interface DataSourceBuilder {

    DataSource build();

    DataSourceBuilder username(String username);

    DataSourceBuilder password(String password);

    DataSourceBuilder url(String url);

    DataSourceBuilder schema(String schema);

    DataSourceBuilder poolSize(int size);

    DataSourceBuilder autoCommit(boolean autoCommit);

    DataSourceBuilder copy(DataSource ds);

    String username();

    String password();

    String url();

    String schema();

    int poolSize();

    boolean autoCommit();

    DataSourceBuilder clear();
}
