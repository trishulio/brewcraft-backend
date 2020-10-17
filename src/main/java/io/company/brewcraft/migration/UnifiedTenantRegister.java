package io.company.brewcraft.migration;

public class UnifiedTenantRegister implements TenantRegister {

    private TenantRegister userReg;
    private TenantRegister schemaReg;

    public UnifiedTenantRegister(TenantUserRegister userReg, TenantSchemaRegister schemaReg) {
        this.userReg = userReg;
        this.schemaReg = schemaReg;
    }

    @Override
    public void add(String tenantId) {
        userReg.add(tenantId);
        schemaReg.add(tenantId);
    }

    @Override
    public void remove(String tenantId) {
        schemaReg.remove(tenantId);
        userReg.remove(tenantId);
    }

    @Override
    public boolean exists(String tenantId) {
        return userReg.exists(tenantId) && schemaReg.exists(tenantId);
    }
}
