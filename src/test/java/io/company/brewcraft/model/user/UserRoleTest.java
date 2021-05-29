package io.company.brewcraft.model.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserRoleTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserRoleBinding userRole = new UserRoleBinding();
        userRole.setId(id);
        assertEquals(id, userRole.getId());
    }

    @Test
    public void testSetGetUserRole() {
        final UserRole userRole = new UserRole();
        final UserRoleBinding userRole = new UserRoleBinding();
        userRole.setUserRole(userRole);
        assertEquals(userRole, userRole.getUserRole());
    }

    @Test
    public void testSetGetUser() {
        final User user = new User();
        final UserRoleBinding userRole = new UserRoleBinding();
        userRole.setUser(user);
        assertEquals(user, userRole.getUser());
    }

    @Test
    public void testSetGetVersion() {
        final Integer version = 1;
        final UserRoleBinding userRole = new UserRoleBinding();
        userRole.setVersion(version);
        assertEquals(version, userRole.getVersion());
    }

    @Test
    public void testSetGetLastUpdated() {
        final LocalDateTime lastUpdatedDateTime = LocalDateTime.now();
        final UserRoleBinding userRole = new UserRoleBinding();
        userRole.setLastUpdated(lastUpdatedDateTime);
        assertEquals(lastUpdatedDateTime, userRole.getLastUpdated());
    }

    @Test
    public void testSetGetCreatedAt() {
        final LocalDateTime createdAtDateTime = LocalDateTime.now();
        final UserRoleBinding userRole = new UserRoleBinding();
        userRole.setCreatedAt(createdAtDateTime);
        assertEquals(createdAtDateTime, userRole.getCreatedAt());
    }
}