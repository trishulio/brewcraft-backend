package io.company.brewcraft.model.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
    public void testSetGetUserRoleType() {
        final UserRoleType userRoleType = new UserRoleType();
        final UserRole userRole = new UserRole();
        userRole.setUserRoleType(userRoleType);
        assertEquals(userRoleType, userRole.getUserRoleType());
    }

    @Test
    public void testSetGetUser() {
        final User user = new User();
        final UserRole userRole = new UserRole();
        userRole.setUser(user);
        assertEquals(user, userRole.getUser());
    }

    @Test
    public void testSetGetVersion() {
        final Integer version = 1;
        final UserRole userRole = new UserRole();
        userRole.setVersion(version);
        assertEquals(version, userRole.getVersion());
    }

    @Test
    public void testSetGetLastUpdated() {
        final LocalDateTime lastUpdatedDateTime = LocalDateTime.now();
        final UserRole userRole = new UserRole();
        userRole.setLastUpdated(lastUpdatedDateTime);
        assertEquals(lastUpdatedDateTime, userRole.getLastUpdated());
    }

    @Test
    public void testSetGetCreatedAt() {
        final LocalDateTime createdAtDateTime = LocalDateTime.now();
        final UserRole userRole = new UserRole();
        userRole.setCreatedAt(createdAtDateTime);
        assertEquals(createdAtDateTime, userRole.getCreatedAt());
    }
}