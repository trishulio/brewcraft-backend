package io.company.brewcraft.dto.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddUserDtoTest {

    @Test
    public void testSetGetUserName() {
        final String userName = "userName";
        final AddUserDto user = new AddUserDto();
        user.setUserName(userName);
        assertEquals(userName, user.getUserName());
    }

    @Test
    public void testSetGetDisplayName() {
        final String displayName = "displayName";
        final AddUserDto user = new AddUserDto();
        user.setDisplayName(displayName);
        assertEquals(displayName, user.getDisplayName());
    }

    @Test
    public void testSetGetFirstName() {
        final String firstName = "firstName";
        final AddUserDto user = new AddUserDto();
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void testSetGetLastName() {
        final String lastName = "lastName";
        final AddUserDto user = new AddUserDto();
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void testSetGetEmail() {
        final String email = "email";
        final AddUserDto user = new AddUserDto();
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetGetImageUrl() {
        final String imageUrl = "imageUrl";
        final AddUserDto user = new AddUserDto();
        user.setImageUrl(imageUrl);
        assertEquals(imageUrl, user.getImageUrl());
    }

    @Test
    public void testSetGetPhoneNumber() {
        final String phoneNumber = "phoneNumber";
        final AddUserDto user = new AddUserDto();
        user.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testSetGetStatus() {
        final FixedTypeDto status = new FixedTypeDto();
        final AddUserDto user = new AddUserDto();
        user.setStatus(status);
        assertEquals(status, user.getStatus());
    }

    @Test
    public void testSetGetSalutation() {
        final FixedTypeDto salutation = new FixedTypeDto();
        final AddUserDto user = new AddUserDto();
        user.setSalutation(salutation);
        assertEquals(salutation, user.getSalutation());
    }

    @Test
    public void testSetGetRoles() {
        final AddUserRoleDto userRole = new AddUserRoleDto();
        final AddUserDto user = new AddUserDto();
        user.setRoles(Collections.singletonList(userRole));
        assertEquals(Collections.singletonList(userRole), user.getRoles());
    }
}