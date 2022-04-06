package io.company.brewcraft.security.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.security.session.PrincipalContext;

public class CognitoPrincipalContextTest {
    static final String IAAS_TOKEN = "IAAS_TOKEN";

    private PrincipalContext ctx;
    private Jwt mJwt;

    @BeforeEach
    public void init() {
        mJwt = mock(Jwt.class);
        doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
        doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

        ctx = new CognitoPrincipalContext(mJwt, IAAS_TOKEN);
    }

    @Test
    public void testConstructor_ThrowsError_WhenMultipleGroupValuesArePresent() {
        mJwt = mock(Jwt.class);
        doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
        doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

        assertThrows(IllegalArgumentException.class, () -> new CognitoPrincipalContext(mJwt, IAAS_TOKEN), "ach user should only belong to a single cognito group. Instead found 2");
    }

    @Test
    public void testGetTenantId_ReturnsTheFirstGroupValueInJwt_WhenSingleGroupIsPresent() {
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), ctx.getTenantId());
    }

    @Test
    public void testGetUsername_ReturnsUserValue() {
        assertEquals("USERNAME_1", ctx.getUsername());
    }

    @Test
    public void testGetRoles_ReturnsTheCognitoScopes() {
        assertEquals(Arrays.asList("SCOPE_1", "SCOPE_2"), ctx.getRoles());
    }

    @Test
    public void testGetIaasLogin_ReturnsIaasToken() {
        assertEquals(IAAS_TOKEN, ctx.getIaasLogin());
    }
}
