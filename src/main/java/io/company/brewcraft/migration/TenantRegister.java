package io.company.brewcraft.migration;

public interface TenantRegister {

    boolean userExists(String tenantId);

    boolean isRegisteredTenant(String tenantId);

    void registerUser(String tenantId);

    void registerTenant(String tenantId);

    void registerApp();

    void removeUser(String tenantId);

    void deregister(String tenantId);
}
