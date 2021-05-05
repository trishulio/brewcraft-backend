package io.company.brewcraft.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserSalutationTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserSalutation userSalutation = new UserSalutation();
        userSalutation.setId(id);
        assertEquals(id, userSalutation.getId());
    }

    @Test
    public void testSetGetName() {
        final String name = "name";
        final UserSalutation userSalutation = new UserSalutation();
        userSalutation.setName(name);
        assertEquals(name, userSalutation.getName());
    }
}