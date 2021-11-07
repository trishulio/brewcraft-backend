package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.service.mapper.user.UserRoleMapper;

public class UserRoleMapperTest {

    private UserRoleMapper mapper;

    @BeforeEach
    public void init() {
        mapper = UserRoleMapper.INSTANCE;
    }

    @Test
    public void testFromDto_CreatesRoleWithId_WhenIdIsNotNull() {
        UserRole role = mapper.fromDto(1L);

        UserRole expected = new UserRole(1L);

        assertEquals(expected, role);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((Long) null));
    }

    @Test
    public void testToDto_ReturnsRoleDto_WhenPojoIsNotNull() {
        UserRole role = new UserRole(
            1L,
            "TITLE",
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        UserRoleDto dto = mapper.toDto(role);
        UserRoleDto expected = new UserRoleDto(
            1L,
            "TITLE",
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
