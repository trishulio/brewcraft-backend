package io.company.brewcraft.security.auth;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.UtilityProviderFilter;
import io.company.brewcraft.util.UtilityProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/public/**", "/static/**", "/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
    }

    @Bean
    public JwtDecoder decoder(OAuth2ResourceServerProperties props) {
        String jwkUri = props.getJwt().getJwkSetUri();
        return NimbusJwtDecoder.withJwkSetUri(jwkUri).build();
    }

    @Bean
    public Filter ctxHolderFilter(ContextHolder ctxHolder) {
        return new ContextHolderFilter(ctxHolder);
    }

    @Bean
    public Filter utilityFilter(UtilityProvider utilityProvider) {
        return new UtilityProviderFilter(utilityProvider);
    }
}
