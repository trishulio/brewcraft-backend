package io.company.brewcraft.security.auth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import io.company.brewcraft.model.LazyIaasIdpTenant;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.security.session.LazyTenantContext;
import io.company.brewcraft.security.session.PrincipalContext;
import io.company.brewcraft.security.session.TenantContext;
import io.company.brewcraft.security.session.ThreadLocalContextHolder;
import io.company.brewcraft.service.impl.IdpTenantIaasRepository;
import io.company.brewcraft.service.impl.TenantManagementService;

public class ContextHolderFilter implements Filter {
    public static final String HEADER_NAME_IAAS_TOKEN = "X-Iaas-Token";

    private ThreadLocalContextHolder ctxHolder;
    private TenantManagementService tenantService;
    private IdpTenantIaasRepository iaasIdpRepo;

    public ContextHolderFilter(ThreadLocalContextHolder ctxHolder, TenantManagementService tenantService, IdpTenantIaasRepository iaasIdpRepo) {
        this.ctxHolder = ctxHolder;
        this.tenantService = tenantService;
        this.iaasIdpRepo = iaasIdpRepo;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setPrincipalContext((HttpServletRequest) request);
        setTenantContext();

        chain.doFilter(request, response);
    }

    private void setPrincipalContext(HttpServletRequest request) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        Object principal = auth.getPrincipal();
        PrincipalContext principalCtx = null;
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            principalCtx = new CognitoPrincipalContext(jwt, request.getHeader(HEADER_NAME_IAAS_TOKEN));
        }
        this.ctxHolder.setContext(principalCtx);
    }

    private void setTenantContext() {
        UUID tenantId = this.ctxHolder.getPrincipalContext().getTenantId();

        if (tenantId != null) {
            LazyIaasIdpTenant idpTenant = new LazyIaasIdpTenant(tenantId, this.iaasIdpRepo);
            
            TenantContext ctx = new LazyTenantContext(tenantService, idpTenant, tenantId);
            
            this.ctxHolder.setTenantContext(ctx);
        }
    }
}
