package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UpdateUserDtoTest {

    @Test
    public void testSetGetDisplayName() {
        final String displayName = "displayName";
        final UpdateUserDto user = new UpdateUserDto();
        user.setDisplayName(displayName);
        assertEquals(displayName, user.getDisplayName());
    }

    @Test
    public void testSetGetFirstName() {
        final String firstName = "firstName";
        final UpdateUserDto user = new UpdateUserDto();
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void testSetGetLastName() {
        final String lastName = "lastName";
        final UpdateUserDto user = new UpdateUserDto();
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void testSetGetEmail() {
        final String email = "email";
        final UpdateUserDto user = new UpdateUserDto();
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetGetImageUrl() {
        final String imageUrl = "imageUrl";
        final UpdateUserDto user = new UpdateUserDto();
        user.setImageUrl(imageUrl);
        assertEquals(imageUrl, user.getImageUrl());
    }

    @Test
    public void testSetGetPhoneNumber() {
        final String phoneNumber = "phoneNumber";
        final UpdateUserDto user = new UpdateUserDto();
        user.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testSetGetStatus() {
        final FixedTypeDto status = new FixedTypeDto();
        final UpdateUserDto user = new UpdateUserDto();
        user.setStatus(status);
        assertEquals(status, user.getStatus());
    }

    @Test
    public void testSetGetSalutation() {
        final FixedTypeDto salutation = new FixedTypeDto();
        final UpdateUserDto user = new UpdateUserDto();
        user.setSalutation(salutation);
        assertEquals(salutation, user.getSalutation());
    }

    @Test
    public void testSetGetRoles() {
        final AddUserRoleDto userRole = new AddUserRoleDto();
        final UpdateUserDto user = new UpdateUserDto();
        user.setRoles(Collections.singletonList(userRole));
        assertEquals(Collections.singletonList(userRole), user.getRoles());
    }

    @Test
    public void testSetGetVersion() {
        final Integer version = 1;
        final UpdateUserDto userRole = new UpdateUserDto();
        userRole.setVersion(version);
        assertEquals(version, userRole.getVersion());
    }
}