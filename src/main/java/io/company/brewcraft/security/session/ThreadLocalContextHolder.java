package io.company.brewcraft.security.session;

import java.util.function.Supplier;

import io.company.brewcraft.model.Tenant;

public class ThreadLocalContextHolder implements ContextHolder {
    private InheritableThreadLocal<PrincipalContext> principalCtx;
    private InheritableThreadLocal<Supplier<Tenant>> tenantContextProxy;
    private InheritableThreadLocal<Tenant> tenantCtx;

    public ThreadLocalContextHolder() {
        this.principalCtx = new InheritableThreadLocal<>();
        this.tenantContextProxy = new InheritableThreadLocal<>();
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
    public Tenant getTenantInContext() {
        Tenant tenant = this.tenantCtx.get();
        
        if (tenant == null) {
            Supplier<Tenant> tenantProvider = this.tenantContextProxy.get(); 
            tenant = tenantProvider.get();
            
            this.tenantCtx.set(tenant);
        }

        return tenant;
    }
    
    public void setTenantInContextProxy(Supplier<Tenant> tenantProxy) {
        this.tenantContextProxy.set(tenantProxy);
    }
}
