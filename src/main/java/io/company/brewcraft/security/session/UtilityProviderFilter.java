package io.company.brewcraft.security.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class UtilityProviderFilter implements Filter {

    private UtilityProvider provider;

    public UtilityProviderFilter(UtilityProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.provider.setValidator(new Validator());

        chain.doFilter(request, response);
    }
}
