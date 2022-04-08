package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasAuthorizationDtoTest {
    private IaasAuthorizationDto dto;

    @BeforeEach
    public void init() {
        dto = new IaasAuthorizationDto();
    }

    @Test
    public void testNoArgConstructor_SetsNull() {
        assertNull(dto.getAccessKey());
        assertNull(dto.getAccessSecret());
        assertNull(dto.getExpiration());
        assertNull(dto.getSessionToken());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new IaasAuthorizationDto("ACCESS_KEY", "ACCESS_SECRET", "SESSION_TOKEN", LocalDateTime.of(2000, 1, 1, 0, 0));

        assertEquals("ACCESS_KEY", dto.getAccessKey());
        assertEquals("ACCESS_SECRET", dto.getAccessSecret());
        assertEquals("SESSION_TOKEN", dto.getSessionToken());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
    }

    @Test
    public void testGetSetAccessKey() {
        dto.setAccessKey("ACCESS_KEY");
        assertEquals("ACCESS_KEY", dto.getAccessKey());
    }

    @Test
    public void testGetSetAccessSecret() {
        dto.setAccessSecret("ACCESS_SECRET");
        assertEquals("ACCESS_SECRET", dto.getAccessSecret());
    }

    @Test
    public void testGetSetSessionToken() {
        dto.setSessionToken("SESSION_TOKEN");
        assertEquals("SESSION_TOKEN", dto.getSessionToken());
    }

    @Test
    public void testGetSetExpiration() {
        dto.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
    }
}
