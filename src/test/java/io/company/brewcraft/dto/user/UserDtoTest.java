package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final UserDto user = new UserDto();
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testSetGetUserName() {
        final String userName = "userName";
        final UserDto user = new UserDto();
        user.setUserName(userName);
        assertEquals(userName, user.getUserName());
    }

    @Test
    public void testSetGetDisplayName() {
        final String displayName = "displayName";
        final UserDto user = new UserDto();
        user.setDisplayName(displayName);
        assertEquals(displayName, user.getDisplayName());
    }

    @Test
    public void testSetGetFirstName() {
        final String firstName = "firstName";
        final UserDto user = new UserDto();
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void testSetGetLastName() {
        final String lastName = "lastName";
        final UserDto user = new UserDto();
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void testSetGetEmail() {
        final String email = "email";
        final UserDto user = new UserDto();
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetGetImageUrl() {
        final String imageUrl = "imageUrl";
        final UserDto user = new UserDto();
        user.setImageUrl(imageUrl);
        assertEquals(imageUrl, user.getImageUrl());
    }

    @Test
    public void testSetGetPhoneNumber() {
        final String phoneNumber = "phoneNumber";
        final UserDto user = new UserDto();
        user.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testSetGetStatus() {
        final FixedTypeDto status = new FixedTypeDto();
        final UserDto user = new UserDto();
        user.setStatus(status);
        assertEquals(status, user.getStatus());
    }

    @Test
    public void testSetGetSalutation() {
        final FixedTypeDto salutation = new FixedTypeDto();
        final UserDto user = new UserDto();
        user.setSalutation(salutation);
        assertEquals(salutation, user.getSalutation());
    }

    @Test
    public void testSetGetRoles() {
        final UserRoleDto userRole = new UserRoleDto();
        final UserDto user = new UserDto();
        user.setRoles(Collections.singletonList(userRole));
        assertEquals(Collections.singletonList(userRole), user.getRoles());
    }

    @Test
    public void testSetGetVersion() {
        final Integer version = 1;
        final UserDto userRole = new UserDto();
        userRole.setVersion(version);
        assertEquals(version, userRole.getVersion());
    }
}