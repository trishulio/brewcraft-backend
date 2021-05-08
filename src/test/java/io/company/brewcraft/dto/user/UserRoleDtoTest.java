package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRoleDtoTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserRoleDto userRole = new UserRoleDto();
        userRole.setId(id);
        assertEquals(id, userRole.getId());
    }

    @Test
    public void testSetGetUserRoleType() {
        final FixedTypeDto userRoleType = new FixedTypeDto();
        final UserRoleDto userRole = new UserRoleDto();
        userRole.setUserRoleType(userRoleType);
        assertEquals(userRoleType, userRole.getUserRoleType());
    }
}