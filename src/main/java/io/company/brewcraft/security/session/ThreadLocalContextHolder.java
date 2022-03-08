package io.company.brewcraft.security.session;

public class ThreadLocalContextHolder implements ContextHolder {
    private InheritableThreadLocal<PrincipalContext> principalCtx;
    private InheritableThreadLocal<TenantContext> tenantCtx;

    public ThreadLocalContextHolder() {
        this.principalCtx = new InheritableThreadLocal<>();
        this.tenantCtx = new InheritableThreadLocal<>();
    }

    @Override
    public PrincipalContext getPrincipalContext() {
        return this.principalCtx.get();
    }

    public void setContext(PrincipalContext principalCtx) {
        this.principalCtx.set(principalCtx);
    }

    @Override
    public TenantContext getTenantContext() {
        return this.tenantCtx.get();
    }
    
    public void setTenantContext(TenantContext tenantContext) {
        this.tenantCtx.set(tenantContext);
    }
}
