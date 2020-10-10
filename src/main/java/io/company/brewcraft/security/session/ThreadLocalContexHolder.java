package io.company.brewcraft.security.session;

public class ThreadLocalContexHolder implements ContextHolder {

    InheritableThreadLocal<PrincipalContext> principalCtx;

    public ThreadLocalContexHolder() {
        this.principalCtx = new InheritableThreadLocal<>();
    }

    @Override
    public PrincipalContext getPrincipalContext() {
        return this.principalCtx.get();
    }

    @Override
    public void setContext(PrincipalContext principalCtx) {
        this.principalCtx.set(principalCtx);
    }
}
