package io.company.brewcraft.model;

public class TenantIaasDeleteResult extends BaseModel {
    private TenantIaasAuthDeleteResult auth;
    private TenantIaasIdpDeleteResult idp;
    private TenantIaasVfsDeleteResult vfs;

    public TenantIaasDeleteResult() {
    }

    public TenantIaasDeleteResult(TenantIaasAuthDeleteResult auth, TenantIaasIdpDeleteResult idp, TenantIaasVfsDeleteResult vfs) {
        this();
        setAuth(auth);
        setIdp(idp);
        setVfs(vfs);
    }

    public TenantIaasAuthDeleteResult getAuth() {
        return auth;
    }

    public void setAuth(TenantIaasAuthDeleteResult auth) {
        this.auth = auth;
    }

    public TenantIaasIdpDeleteResult getIdp() {
        return idp;
    }

    public void setIdp(TenantIaasIdpDeleteResult idp) {
        this.idp = idp;
    }

    public TenantIaasVfsDeleteResult getVfs() {
        return vfs;
    }

    public void setVfs(TenantIaasVfsDeleteResult vfs) {
        this.vfs = vfs;
    }
}
