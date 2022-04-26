package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasAuthorizationTest {
    private IaasAuthorization authorization;

    @BeforeEach
    public void init() {
        authorization = new IaasAuthorization();
    }

    @Test
    public void testNoArgConstructor_SetsNull() {
        assertNull(authorization.getAccessKey());
        assertNull(authorization.getAccessSecret());
        assertNull(authorization.getExpiration());
        assertNull(authorization.getSessionToken());
    }

    @Test
    public void testAllArgConstructor() {
        authorization = new IaasAuthorization("ACCESS_KEY", "ACCESS_SECRET", "SESSION_TOKEN", LocalDateTime.of(2000, 1, 1, 0, 0));

        assertEquals("ACCESS_KEY", authorization.getAccessKey());
        assertEquals("ACCESS_SECRET", authorization.getAccessSecret());
        assertEquals("SESSION_TOKEN", authorization.getSessionToken());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), authorization.getExpiration());
    }

    @Test
    public void testGetSetId() {
        authorization.setId("ACCESS_KEY");
        assertEquals("ACCESS_KEY", authorization.getId());
        assertEquals("ACCESS_KEY", authorization.getAccessKey());
    }

    @Test
    public void testGetSetAccessKey() {
        authorization.setAccessKey("ACCESS_KEY");
        assertEquals("ACCESS_KEY", authorization.getAccessKey());
        assertEquals("ACCESS_KEY", authorization.getId());
    }

    @Test
    public void testGetSetAccessSecret() {
        authorization.setAccessSecret("ACCESS_SECRET");
        assertEquals("ACCESS_SECRET", authorization.getAccessSecret());
    }

    @Test
    public void testGetSetSessionToken() {
        authorization.setSessionToken("SESSION_TOKEN");
        assertEquals("SESSION_TOKEN", authorization.getSessionToken());
    }

    @Test
    public void testGetSetExpiration() {
        authorization.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), authorization.getExpiration());
    }

    @Test
    public void testGetVersion_ReturnsNull() {
        assertNull(authorization.getVersion());
    }
}
