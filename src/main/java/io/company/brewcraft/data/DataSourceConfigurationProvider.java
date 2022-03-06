package io.company.brewcraft.data;

public interface DataSourceConfigurationProvider<ID> {
    DataSourceConfiguration getConfiguration(ID id);

    DataSourceConfiguration getAdminConfiguration();
}
