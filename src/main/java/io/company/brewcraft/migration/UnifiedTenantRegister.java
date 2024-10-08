package io.company.brewcraft.migration;

import io.company.brewcraft.model.Tenant;

public class UnifiedTenantRegister implements TenantRegister {
    private TenantRegister userReg;
    private TenantRegister schemaReg;

    public UnifiedTenantRegister(TenantUserRegister userReg, TenantSchemaRegister schemaReg) {
        this.userReg = userReg;
        this.schemaReg = schemaReg;
    }

    @Override
    public void add(Tenant tenant) {
        userReg.add(tenant);
        schemaReg.add(tenant);
    }

    @Override
    public void put(Tenant tenant) {
        userReg.put(tenant);
        schemaReg.put(tenant);
    }

    @Override
    public void remove(Tenant tenant) {
        schemaReg.remove(tenant);
        userReg.remove(tenant);
    }

    @Override
    public boolean exists(Tenant tenant) {
        return userReg.exists(tenant) && schemaReg.exists(tenant);
    }
}
