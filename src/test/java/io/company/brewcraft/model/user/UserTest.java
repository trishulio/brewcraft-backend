package io.company.brewcraft.model.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final User user = new User();
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testSetGetUserName() {
        final String userName = "userName";
        final User user = new User();
        user.setUserName(userName);
        assertEquals(userName, user.getUserName());
    }

    @Test
    public void testSetGetDisplayName() {
        final String displayName = "displayName";
        final User user = new User();
        user.setDisplayName(displayName);
        assertEquals(displayName, user.getDisplayName());
    }

    @Test
    public void testSetGetFirstName() {
        final String firstName = "firstName";
        final User user = new User();
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void testSetGetLastName() {
        final String lastName = "lastName";
        final User user = new User();
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void testSetGetEmail() {
        final String email = "email";
        final User user = new User();
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetGetImageUrl() {
        final String imageUrl = "imageUrl";
        final User user = new User();
        user.setImageUrl(imageUrl);
        assertEquals(imageUrl, user.getImageUrl());
    }

    @Test
    public void testSetGetPhoneNumber() {
        final String phoneNumber = "phoneNumber";
        final User user = new User();
        user.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testSetGetStatus() {
        final UserStatus status = new UserStatus();
        final User user = new User();
        user.setStatus(status);
        assertEquals(status, user.getStatus());
    }

    @Test
    public void testSetGetSalutation() {
        final UserSalutation salutation = new UserSalutation();
        final User user = new User();
        user.setSalutation(salutation);
        assertEquals(salutation, user.getSalutation());
    }

    @Test
    public void testSetRoles_ReplacesExistingRolesWithNewRoles_WhenExistingRolesAlreadySet() {
        final UserRoleBinding existingRole = new UserRoleBinding();
        final User user = new User();
        user.setRoles(Collections.singletonList(existingRole));

        final UserRoleBinding newRole = new UserRoleBinding();
        user.setRoles(Collections.singletonList(newRole));

        assertEquals(Collections.singletonList(newRole), user.getRoles());
    }

    @Test
    public void testSetRoles_AddNewRoles_WhenRolesAreNotSet() {
        final User user = new User();
        final UserRoleBinding newRole = new UserRoleBinding();
        user.setRoles(Collections.singletonList(newRole));
        assertEquals(Collections.singletonList(newRole), user.getRoles());
    }

    @Test
    public void testSetRoles_SetRolesToNull_WhenBothExistingAndNewlyAddingRolesAreNull() {
        final User user = new User();
        user.setRoles(null);
        assertNull(user.getRoles());
    }

    @Test
    public void testSetRoles_SetUserForRoles_WhenRolesAreSet() {
        final User user = new User();
        final UserRoleBinding newRole = new UserRoleBinding();
        user.setRoles(Collections.singletonList(newRole));
        assertEquals(user, user.getRoles().get(0).getUser());
    }

    @Test
    public void testSetGetVersion() {
        final Integer version = 1;
        final User user = new User();
        user.setVersion(version);
        assertEquals(version, user.getVersion());
    }

    @Test
    public void testSetGetLastUpdated() {
        final LocalDateTime lastUpdatedDateTime = LocalDateTime.now();
        final User user = new User();
        user.setLastUpdated(lastUpdatedDateTime);
        assertEquals(lastUpdatedDateTime, user.getLastUpdated());
    }

    @Test
    public void testSetGetCreatedAt() {
        final LocalDateTime createdAtDateTime = LocalDateTime.now();
        final User user = new User();
        user.setCreatedAt(createdAtDateTime);
        assertEquals(createdAtDateTime, user.getCreatedAt());
    }
}
