package io.company.brewcraft.data;

import javax.sql.DataSource;

public interface DataSourceBuilder {

    DataSource build();

    DataSourceBuilder username(String username);

    DataSourceBuilder password(String password);

    DataSourceBuilder url(String url);

    DataSourceBuilder schema(String schema);

    DataSourceBuilder autoCommit(boolean autoCommit);

    String username();

    String password();

    String url();

    String schema();

    boolean autoCommit();

    DataSourceBuilder clear();
}
