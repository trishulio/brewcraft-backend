package io.company.brewcraft.migration;

public interface TenantRegister {

    void add(String tenantId);

    void remove(String tenantId);

    boolean exists(String tenantId);

    void setup();
}
