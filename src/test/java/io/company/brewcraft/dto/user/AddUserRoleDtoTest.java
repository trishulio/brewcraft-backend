package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddUserRoleDtoTest {

    @Test
    public void testSetGetUserRole() {
        final FixedTypeDto userRole = new FixedTypeDto();
        final AddUserRoleDto userRole = new AddUserRoleDto();
        userRole.setUserRole(userRole);
        assertEquals(userRole, userRole.getUserRole());
    }
}