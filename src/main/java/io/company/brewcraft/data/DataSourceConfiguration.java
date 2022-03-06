package io.company.brewcraft.data;

public interface DataSourceConfiguration extends GlobalDataSourceConfiguration {
    String getUserName();

    String getPassword();

    String getSchemaName();
}
