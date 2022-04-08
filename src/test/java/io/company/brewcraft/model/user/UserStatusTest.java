package io.company.brewcraft.model.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserStatusTest {
    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserStatus userStatus = new UserStatus();
        userStatus.setId(id);
        assertEquals(id, userStatus.getId());
    }

    @Test
    public void testSetGetName() {
        final String name = "name";
        final UserStatus userStatus = new UserStatus();
        userStatus.setName(name);
        assertEquals(name, userStatus.getName());
    }
}