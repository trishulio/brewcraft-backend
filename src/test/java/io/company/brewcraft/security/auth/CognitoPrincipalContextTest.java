package io.company.brewcraft.security.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.security.session.PrincipalContext;

public class CognitoPrincipalContextTest {

    private PrincipalContext ctx;
    private Jwt mJwt;

    @BeforeEach
    public void init() {
        mJwt = mock(Jwt.class);
        ctx = new CognitoPrincipalContext(mJwt);
    }

    @Test
    public void testGetTenantId_ReturnsTheFirstGroupValueInJwt_WhenSingleGroupIsPresent() {
        doReturn(Arrays.asList("TENANT_1")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        String tenantId = ctx.getTenantId();
        assertEquals("TENANT_1", tenantId);
    }

    @Test
    public void testGetTenantId_ThrowsError_WhenMultipleGroupValuesArePresent() {
        doReturn(Arrays.asList("TENANT_1", "TENANT_2")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        assertThrows(IllegalStateException.class, () -> ctx.getTenantId());
    }

    @Test
    public void testGetUsername_ReturnsUserValue() {
        doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
        String username = ctx.getUsername();
        assertEquals("USERNAME_1", username);
    }

    @Test
    public void testGetRoles_ReturnsTheCognitoScopes() {
        doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);
        List<String> roles = ctx.getRoles();
        assertEquals(Arrays.asList("SCOPE_1", "SCOPE_2"), roles);
    }
}
