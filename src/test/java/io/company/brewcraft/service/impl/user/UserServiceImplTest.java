package io.company.brewcraft.service.impl.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.user.BaseUser;
import io.company.brewcraft.model.user.BaseUserRole;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;
import io.company.brewcraft.service.IdpUserRepository;

public class UserServiceImplTest {

    private UserRepository mUserRepo;
    private IdpUserRepository idpRepo;
    private ContextHolder contextHolder;
    private PrincipalContext principalContext;
    private Refresher<User, UserAccessor> userRefresher;

    private UserServiceImpl service;

    @BeforeEach
    public void init() {
        mUserRepo = mock(UserRepository.class);
        userRefresher = mock(Refresher.class);
        doAnswer(inv -> inv.getArgument(0, User.class)).when(mUserRepo).saveAndFlush(any(User.class));

        idpRepo = mock(IdpUserRepository.class);
        contextHolder = mock(ContextHolder.class);
        principalContext = mock(PrincipalContext.class);

        when(contextHolder.getPrincipalContext()).thenReturn(principalContext);
        when(principalContext.getTenantId()).thenReturn("tenant-uuid");

        service = new UserServiceImpl(mUserRepo, idpRepo, userRefresher, contextHolder);
    }

    @Test
    public void testGetusersCallsRepositoryWithACustomSpec_AndReturnsPageOfEntities() {
        ArgumentCaptor<Specification<User>> captor = ArgumentCaptor.forClass(Specification.class);
        Page<User> mPage = new PageImpl<>(List.of(new User(1L)));
        doReturn(mPage).when(mUserRepo).findAll(captor.capture(), eq(PageRequest.of(1, 10, Direction.ASC, "col1", "col2")));

        Page<User> page = service.getUsers(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("USERNAME"), // userNames
            Set.of("DISPLAY_NAME"), // displayNames
            Set.of("EMAIL"), // emails
            Set.of("PHONE_NUMBER"), // phoneNumbers
            Set.of(3L), // statusIds
            Set.of(4L), // salutationIds
            Set.of(), // roles
            1, // page
            10, // size
            new TreeSet<>(Set.of("col1", "col2")), // sort
            true// orderAscending
        );

        // TODO: Test spec
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

        verify(idpRepo, times(1)).createUserInGroup(expected, "tenant-uuid");
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
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            null, //email update should be ignored as it will cause out of sync issue with cognito
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
            "USER_NAME",
            null,
            "FIRST_NAME",
            "LAST_NAME",
            null, //email update should be ignored as it will cause out of sync issue with cognito
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
    public void testPutUser_Throws_WhenNoExistingUserInRepo() {
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

            assertThrows(RuntimeException.class, () -> service.putUser(1L, user));
    }

    @Test
    public void testPutUserThrowsOptimisticLockExpection_WhenExistingUserVersionIsMismatched() {
        User existing = new User(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mUserRepo).findById(1L);

        UpdateUser<? extends UpdateUserRole> user = new User();
        ((User) user).setVersion(2); // Different from existing

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
            "EMAIL_NEW",
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
            "USER_NAME_NEW",
            "DISPLAY_NAME_NEW",
            "FIRST_NAME",
            "LAST_NAME_NEW",
            "EMAIL", //email update should be ignored as it will cause out of sync issue with cognito
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