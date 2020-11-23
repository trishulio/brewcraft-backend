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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2ResourceServerProperties props;
    private ContextHolder ctxHolder;

    public WebSecurityConfig(OAuth2ResourceServerProperties props, ContextHolder ctxHolder) {
        this.props = props;
        this.ctxHolder = ctxHolder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/public/**", "/static/**", "/v2/api-docs", "/swagger-ui/**", "/swagger-resources/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
    }

    @Bean
    public JwtDecoder decoder() {
        String jwkUri = props.getJwt().getJwkSetUri();
        return NimbusJwtDecoder.withJwkSetUri(jwkUri).build();
    }

    @Bean
    public Filter ctxHolderFilter() {
        return new ContextHolderFilter(this.ctxHolder);
    }
}
