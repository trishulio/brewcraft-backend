package io.company.brewcraft.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.ThreadLocalContexHolder;

@Configuration
public class AuthConfiguration {

    @Bean
    public ContextHolder ctxHolder() {
        return new ThreadLocalContexHolder();
    }
}
