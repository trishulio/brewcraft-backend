package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.user.AddUserRoleDto;

public class AddRoleDtoTest {
    private AddUserRoleDto dto;

    @BeforeEach
    public void init() {
        dto = new AddUserRoleDto();
    }

    @Test
    public void testAllArgs_SetsAllFields() {
        dto = new AddUserRoleDto("ROLE_NAME");

        assertEquals("ROLE_NAME", dto.getName());
    }

    @Test
    public void testAccessName() {
        assertNull(dto.getName());
        dto.setName("roleName");
        assertEquals("roleName", dto.getName());
    }
}
