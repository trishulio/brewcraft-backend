package io.company.brewcraft.security.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.ThreadLocalContextHolder;

public class AuthConfigurationTest {
    private AuthConfiguration config;

    @BeforeEach
    public void init() {
        config = new AuthConfiguration();
    }

    @Test
    public void testCtxHolder_ReturnsInstanceOfTypeThreadLocalContextHolder() {
        ContextHolder holder = config.ctxHolder();
        assertTrue(holder instanceof ThreadLocalContextHolder);
    }
}
