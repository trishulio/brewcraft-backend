package io.company.brewcraft.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserRoleTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserRole userRole = new UserRole();
        userRole.setId(id);
        assertEquals(id, userRole.getId());
    }

    @Test
    public void testSetGetName() {
        final String name = "name";
        final UserRole userRole = new UserRole();
        userRole.setName(name);
        assertEquals(name, userRole.getName());
    }
}