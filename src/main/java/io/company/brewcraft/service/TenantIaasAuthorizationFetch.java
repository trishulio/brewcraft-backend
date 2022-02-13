package io.company.brewcraft.service;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;

public class TenantIaasAuthorizationFetch {
    private IaasAuthorizationFetch fetcher;
    private ContextHolder contextHolder;

    public TenantIaasAuthorizationFetch(IaasAuthorizationFetch fetcher, ContextHolder contextHolder) {
        this.fetcher = fetcher;
        this.contextHolder = contextHolder;
    }

    public IaasAuthorization fetch() {
        PrincipalContext ctx = this.contextHolder.getPrincipalContext();

        return this.fetcher.fetch(ctx.getIaasToken());
    }
}
