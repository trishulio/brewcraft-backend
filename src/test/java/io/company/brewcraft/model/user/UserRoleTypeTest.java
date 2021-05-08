package io.company.brewcraft.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserRoleTypeTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserRoleType userRoleType = new UserRoleType();
        userRoleType.setId(id);
        assertEquals(id, userRoleType.getId());
    }

    @Test
    public void testSetGetName() {
        final String name = "name";
        final UserRoleType userRoleType = new UserRoleType();
        userRoleType.setName(name);
        assertEquals(name, userRoleType.getName());
    }
}