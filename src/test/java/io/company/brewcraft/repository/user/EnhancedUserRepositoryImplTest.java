package io.company.brewcraft.repository.user;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.user.impl.EnhancedUserRepositoryImpl;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnhancedUserRepositoryImplTest {

    private UserRepository userRepository;

    private UserStatusRepository userStatusRepository;

    private UserSalutationRepository userSalutationRepository;

    private UserRoleTypeRepository userRoleTypeRepository;

    private EnhancedUserRepositoryImpl enhancedUserRepositoryImpl;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        userStatusRepository = mock(UserStatusRepository.class);
        userSalutationRepository = mock(UserSalutationRepository.class);
        userRoleTypeRepository = mock(UserRoleTypeRepository.class);
        enhancedUserRepositoryImpl = new EnhancedUserRepositoryImpl(userRepository, userStatusRepository, userSalutationRepository, userRoleTypeRepository, null);
    }

    @Test
    public void testMapAndSave_WhenStatusIsSetInGivenUser_SaveUserWithMappedStatus() {
        final String userStatusName = "testUserStatus";
        final User user = getUser(userStatusName, null, null);
        UserStatus savedUserStatus = new UserStatus();
        when(userStatusRepository.findByName(userStatusName)).thenReturn(Optional.of(savedUserStatus));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.saveAndFlush(userCaptor.capture())).thenReturn(new User());

        enhancedUserRepositoryImpl.mapAndSave(user);
        final User mappedUser = userCaptor.getValue();

        assertEquals(savedUserStatus, mappedUser.getStatus());
    }

    @Test
    public void testMapAndSave_WhenStatusIsNotSetInGivenUser_SaveUserWithDefaultMappedStatus() {
        final User user = new User();
        UserStatus defaultSavedUserStatus = new UserStatus();
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(defaultSavedUserStatus));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.saveAndFlush(userCaptor.capture())).thenReturn(new User());

        enhancedUserRepositoryImpl.mapAndSave(user);
        final User mappedUser = userCaptor.getValue();

        assertEquals(defaultSavedUserStatus, mappedUser.getStatus());
    }

    @Test
    public void testMapAndSave_WhenStatusNameIsNotSetInGivenUser_SaveUserWithDefaultMappedStatus() {
        final User user = getUser("", null, null);
        UserStatus defaultSavedUserStatus = new UserStatus();
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(defaultSavedUserStatus));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.saveAndFlush(userCaptor.capture())).thenReturn(new User());

        enhancedUserRepositoryImpl.mapAndSave(user);
        final User mappedUser = userCaptor.getValue();

        assertEquals(defaultSavedUserStatus, mappedUser.getStatus());
    }

    @Test
    public void testMapAndSave_WhenStatusSetInGivenUserNotFound_ThrowsEntityNotFoundException() {
        final String invalidUserStatusName = "invalidTestUserStatus";
        final User user = getUser(invalidUserStatusName, null, null);
        when(userStatusRepository.findByName(invalidUserStatusName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> enhancedUserRepositoryImpl.mapAndSave(user));
    }


    @Test
    public void testMapAndSave_WhenSalutationSetWithoutNameForGivenUser_ThrowsIllegalArgumentException() {
        final User user = getUser(null, "", null);
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(new UserStatus()));

        assertThrows(IllegalArgumentException.class, () -> enhancedUserRepositoryImpl.mapAndSave(user));
    }

    @Test
    public void testMapAndSave_WhenSalutationNotFoundForGivenUser_ThrowsIllegalArgumentException() {
        final String invalidSalutationName = "invalidSalutationName";
        final User user = getUser(null, invalidSalutationName, null);
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(new UserStatus()));
        when(userSalutationRepository.findByName(invalidSalutationName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> enhancedUserRepositoryImpl.mapAndSave(user));
    }

    @Test
    public void testMapAndSave_WhenSalutationIsSetInGivenUser_SaveUserWithMappedSalutation() {
        final String salutationName = "salutationName";
        final User user = getUser(null, salutationName, null);
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(new UserStatus()));
        final UserSalutation savedUserSalutation = new UserSalutation();
        when(userSalutationRepository.findByName(salutationName)).thenReturn(Optional.of(savedUserSalutation));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.saveAndFlush(userCaptor.capture())).thenReturn(new User());

        enhancedUserRepositoryImpl.mapAndSave(user);
        final User mappedUser = userCaptor.getValue();

        assertEquals(savedUserSalutation, mappedUser.getSalutation());
    }

    @Test
    public void testMapAndSave_WhenUserRoleNameIsNotSetInGivenUser_ThrowsIllegalArgumentException() {
        final User user = getUser(null, null, "");
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(new UserStatus()));

        assertThrows(IllegalArgumentException.class, () -> enhancedUserRepositoryImpl.mapAndSave(user));
    }

    @Test
    public void testMapAndSave_WhenUserRoleNameIsNotFoundForGivenUser_ThrowsEntityNotFoundException() {
        final String invalidToleTypeName = "invalidRoleType";
        final User user = getUser(null, null, invalidToleTypeName);
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(new UserStatus()));
        when(userRoleTypeRepository.findByName(invalidToleTypeName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> enhancedUserRepositoryImpl.mapAndSave(user));
    }

    @Test
    public void testMapAndSave_WhenUserRoleTypeIsSetInGivenUser_SaveUserWithMappedUserRoleType() {
        final String userRoleTypeName = "userRoleName";
        final User user = getUser(null, null, userRoleTypeName);
        when(userStatusRepository.findByName(UserStatus.DEFAULT_STATUS_NAME)).thenReturn(Optional.of(new UserStatus()));
        final UserRoleType savedUserRole = new UserRoleType();
        when(userRoleTypeRepository.findByName(userRoleTypeName)).thenReturn(Optional.of(savedUserRole));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.saveAndFlush(userCaptor.capture())).thenReturn(new User());

        enhancedUserRepositoryImpl.mapAndSave(user);
        final User mappedUser = userCaptor.getValue();

        final Optional<UserRole> mappedUserRole = mappedUser.getRoles().stream().findFirst();
        assertTrue(mappedUserRole.isPresent());
        assertEquals(savedUserRole, mappedUserRole.get().getUserRoleType());
    }


    private User getUser(final String statusName, final String salutationName, final String roleName) {
        final User user = new User();
        if (Objects.nonNull(statusName)) {
            user.setStatus(getUserStatus(statusName));
        }
        if (Objects.nonNull(salutationName)) {
            user.setSalutation(getUserSalutation(salutationName));
        }
        if (Objects.nonNull(roleName)) {
            user.setRoles(Collections.singletonList(getUserRole(roleName)));
        }
        return user;
    }

    private UserStatus getUserStatus(final String statusName) {
        final UserStatus userStatus = new UserStatus();
        userStatus.setName(statusName);
        return userStatus;
    }

    private UserSalutation getUserSalutation(final String salutationName) {
        final UserSalutation userSalutation = new UserSalutation();
        userSalutation.setName(salutationName);
        return userSalutation;
    }

    private UserRole getUserRole(final String roleName) {
        final UserRoleType userRoleType = new UserRoleType();
        userRoleType.setName(roleName);
        final UserRole userRole = new UserRole();
        userRole.setUserRoleType(userRoleType);
        return userRole;
    }
}
