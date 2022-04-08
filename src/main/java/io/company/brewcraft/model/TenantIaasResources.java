package io.company.brewcraft.model;

public class TenantIaasResources extends BaseModel {
    private TenantIaasAuthResources authResources;
    private TenantIaasIdpResources idpResources;
    private TenantIaasVfsResources vfsResources;

    public TenantIaasResources() {
    }

    public TenantIaasResources(TenantIaasAuthResources authResources, TenantIaasIdpResources idpResources, TenantIaasVfsResources vfsResources) {
        this();
        setAuthResources(authResources);
        setIdpResources(idpResources);
        setVfsResources(vfsResources);
    }

    public TenantIaasAuthResources getAuthResources() {
        return authResources;
    }

    public void setAuthResources(TenantIaasAuthResources authResources) {
        this.authResources = authResources;
    }

    public TenantIaasIdpResources getIdpResources() {
        return idpResources;
    }

    public void setIdpResources(TenantIaasIdpResources idpResources) {
        this.idpResources = idpResources;
    }

    public TenantIaasVfsResources getVfsResources() {
        return vfsResources;
    }

    public void setVfsResources(TenantIaasVfsResources vfsResources) {
        this.vfsResources = vfsResources;
    }
}
