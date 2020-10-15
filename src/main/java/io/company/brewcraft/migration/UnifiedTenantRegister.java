package io.company.brewcraft.migration;

public class UnifiedTenantRegister implements TenantRegister {

    private TenantRegister userReg;
    private TenantRegister schemaReg;
    private TenantRegister fwReg;

    public UnifiedTenantRegister(TenantUserRegister userReg, TenantSchemaRegister schemaReg, FlywayTenantMigrationRegister fwReg) {
        this.userReg = userReg;
        this.schemaReg = schemaReg;
        this.fwReg = fwReg;
    }

    @Override
    public void add(String tenantId) {
        userReg.add(tenantId);
        schemaReg.add(tenantId);
        fwReg.add(tenantId);
    }

    @Override
    public void remove(String tenantId) {
        // TODO: How to handle a single operation failure?
        fwReg.remove(tenantId);
        schemaReg.remove(tenantId);
        userReg.remove(tenantId);
    }

    @Override
    public boolean exists(String tenantId) {
        return userReg.exists(tenantId) &&
               schemaReg.exists(tenantId) &&
               fwReg.exists(tenantId);
    }

    @Override
    public void setup() {
        userReg.setup();
        schemaReg.setup();
        fwReg.setup();
    }
}
