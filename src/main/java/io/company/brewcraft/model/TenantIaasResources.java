package io.company.brewcraft.model;

public class TenantIaasResources {

    private TenantIaasVfsResources vfsResources;
    
    public TenantIaasResources(TenantIaasVfsResources vfsResources) {
        this.vfsResources = vfsResources;
    }

    public TenantIaasVfsResources getVfsResources() {
        return vfsResources;
    }

    public void setVfsResources(TenantIaasVfsResources vfsResources) {
        this.vfsResources = vfsResources;
    }
}
