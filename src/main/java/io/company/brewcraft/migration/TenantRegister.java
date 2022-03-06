package io.company.brewcraft.migration;

import io.company.brewcraft.model.Tenant;

public interface TenantRegister {

    void add(Tenant tenant);

    void put(Tenant tenant);

    void remove(Tenant tenant);

    boolean exists(Tenant tenant);
}
