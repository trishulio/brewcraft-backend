package io.company.brewcraft.security.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;

public class ContextHolderFilter implements Filter {

    private ContextHolder ctxHolder;

    public ContextHolderFilter(ContextHolder ctxHolder) {
        this.ctxHolder = ctxHolder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setPrincipalContext();

        chain.doFilter(request, response);
    }

    private void setPrincipalContext() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        Object principal = auth.getPrincipal();
        PrincipalContext principalCtx = null;
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            principalCtx = new CognitoPrincipalContext(jwt);
        }
        this.ctxHolder.setContext(principalCtx);
    }

}
