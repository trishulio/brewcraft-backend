package io.company.brewcraft.security.session;

public class ThreadLocalContextHolder implements ContextHolder {
    private InheritableThreadLocal<PrincipalContext> principalCtx;

   public ThreadLocalContextHolder() {
        this.principalCtx = new InheritableThreadLocal<>();
    }

    @Override
    public PrincipalContext getPrincipalContext() {
        return this.principalCtx.get();
    }

    public void setContext(PrincipalContext principalCtx) {
        this.principalCtx.set(principalCtx);
    }
}
