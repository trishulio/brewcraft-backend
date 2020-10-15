package io.company.brewcraft.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;
import io.company.brewcraft.security.session.ThreadLocalContextHolder;

public class TenantIdentifierResolverTest {

    private ContextHolder contextHolderMock;

    private PrincipalContext principalContextMock;

    private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;

    @BeforeEach
    public void init() {
        contextHolderMock = mock(ThreadLocalContextHolder.class);
        principalContextMock = mock(CognitoPrincipalContext.class);

        currentTenantIdentifierResolver = new TenantIdentifierResolver(contextHolderMock);
    }

    @Test
    public void testResolveCurrentTenantIdentifier_returnsTenantId() {
        when(contextHolderMock.getPrincipalContext()).thenReturn(principalContextMock);
        when(principalContextMock.getTenantId()).thenReturn("testId");

        String currentTenantIdentifer = currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        assertEquals("testId", currentTenantIdentifer);
    }

    @Test
    public void testResolveCurrentTenantIdentifier_returnsNullWhenPrincipalContextIsNull() {
        when(contextHolderMock.getPrincipalContext()).thenReturn(null);

        String currentTenantIdentifer = currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        assertEquals("null", currentTenantIdentifer);
    }

    @Test
    public void testValidateExistingCurrentSessions_returnsTrue() {
        boolean validate = currentTenantIdentifierResolver.validateExistingCurrentSessions();

        assertTrue(validate);
    }
}
