package io.company.brewcraft.service.impl.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.BaseUser;
import io.company.brewcraft.model.user.BaseUserRole;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.IdpUserRepository;

public class UserServiceImplTest {

    private UserRepository mUserRepo;
    private IdpUserRepository idpRepo;

    private UserServiceImpl service;


    @BeforeEach
    public void init() {
        mUserRepo = mock(UserRepository.class);
        doAnswer(inv -> inv.getArgument(0, User.class)).when(mUserRepo).saveAndFlush(any(User.class));
        
        idpRepo = mock(IdpUserRepository.class);

        service = new UserServiceImpl(mUserRepo, idpRepo);
    }
    
    @Test
    @Disabled(value = "TODO: Need to figure out a way to assert the spec behaviour based on the inputs")
    public void testGetusersCallsRepositoryWithACustomSpec_AndReturnsPageOfEntities() {
        fail("TODO: Need to figure out a way to assert the spec behaviour based on the inputs");
    }
    
    @Test
    public void testGetUser_ReturnsUser_WhenUserExistInRepo() {
        User user = new User(
            1L,
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );
        doReturn(Optional.of(user)).when(mUserRepo).findById(1L);
        
        User expected = new User(
                1L,
                "USER_NAME",
                "DISPLAY_NAME",
                "FIRST_NAME",
                "LAST_NAME",
                "EMAIL",
                "PHONE_NUMBER",
                "IMAGE_URL",
                new UserStatus(1L),
                new UserSalutation(2L),
                List.of(new UserRole(3L)),
                LocalDateTime.of(1999, 1, 1, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                1
            );
        assertEquals(expected, service.getUser(1L));
    }
    
    @Test
    public void testGetUser_ReturnsNull_WhenUserDoesNotExistInRepo() {
        doReturn(Optional.empty()).when(mUserRepo).findById(1L);
        
        assertNull(service.getUser(1L));
    }
    
    @Test
    public void testAddUser_AddUserInRepoAndIdRepo() {
        BaseUser<? extends BaseUserRole> user = new User(
            1L,
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        User added = service.addUser(user);
        
        User expected = new User(
            null,
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            null,
            null,
            null
        );
        
        assertEquals(expected, added);
        
        verify(idpRepo, times(1)).createUser(expected);
    }
    
    @Test
    public void testPutUser_UpdatesExistingUserInRepo_WhenUserExistInRepo() {
        User existing = new User(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mUserRepo).findById(1L);

        UpdateUser<? extends UpdateUserRole> user = new User(
            1L,
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        User updated = service.putUser(1L, user);
        
        User expected = new User(
            1L,
            null, // user-name update will make it out of sync from cognito.
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            null,
            null,
            1
        );
        assertEquals(expected, updated);
        
        verifyNoInteractions(idpRepo);
    }
    
    @Test
    public void testPutUser_UpdatesExistingWithNullValues_WhenPayloadContainsNullFields() {
        User existing = new User(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mUserRepo).findById(1L);

        UpdateUser<? extends UpdateUserRole> user = new User(
            1L,
            "USER_NAME",
            null,
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            null,
            null,
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        User updated = service.putUser(1L, user);
        
        User expected = new User(
            1L,
            null, // user-name update will make it out of sync from cognito.
            null,
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            null,
            null,
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            null,
            null,
            1
        );
        assertEquals(expected, updated);
        
        verifyNoInteractions(idpRepo);
    }

    @Test
    public void testPutUser_AddsNewUser_WhenNoExistingUserInRepo() {
        doReturn(Optional.empty()).when(mUserRepo).findById(1L);

        UpdateUser<? extends UpdateUserRole> user = new User(
                1L,
                "USER_NAME",
                "DISPLAY_NAME",
                "FIRST_NAME",
                "LAST_NAME",
                "EMAIL",
                "PHONE_NUMBER",
                "IMAGE_URL",
                new UserStatus(1L),
                new UserSalutation(2L),
                List.of(new UserRole(3L)),
                LocalDateTime.of(1999, 1, 1, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                1
            );

            User added = service.putUser(1L, user);
            
            User expected = new User(
                1L,
                "USER_NAME",
                "DISPLAY_NAME",
                "FIRST_NAME",
                "LAST_NAME",
                "EMAIL",
                "PHONE_NUMBER",
                "IMAGE_URL",
                new UserStatus(1L),
                new UserSalutation(2L),
                List.of(new UserRole(3L)),
                null,
                null,
                null
            );
            
            assertEquals(expected, added);
            
            verify(idpRepo, times(1)).createUser(expected);
    }

    @Test
    public void testPutUserThrowsOptimisticLockExpection_WhenExistingUserVersionIsMismatched() {
        User existing = new User(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mUserRepo).findById(1L);

        UpdateUser<? extends UpdateUserRole> user = new User(1L);
        user.setVersion(2); // Different from existing

        assertThrows(OptimisticLockException.class, () -> service.putUser(1L, user));
    }
    
    @Test
    public void testPatchUser_PatchesUser_WhenUserExistInRepo() {
        User existing = new User(
            1L,
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mUserRepo).findById(1L);

        UpdateUser<? extends UpdateUserRole> user = new User(
            1L,
            "USER_NAME_NEW",
            "DISPLAY_NAME_NEW",
            null,
            "LAST_NAME_NEW",
            null,
            "PHONE_NUMBER_NEW",
            null,
            null,
            null,
            List.of(new UserRole(4L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        User updated = service.patchUser(1L, user);
        
        User expected = new User(
            1L,
            "USER_NAME",
            "DISPLAY_NAME_NEW",
            "FIRST_NAME",
            "LAST_NAME_NEW",
            "EMAIL",
            "PHONE_NUMBER_NEW",
            "IMAGE_URL",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(4L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );
        assertEquals(expected, updated);
        
        verifyNoInteractions(idpRepo);
    }
}
