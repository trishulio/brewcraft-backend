package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.AddUserRoleDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.dto.user.UserSalutationDto;
import io.company.brewcraft.dto.user.UserStatusDto;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.user.UserMapper;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.controller.AttributeFilter;

public class UserControllerTest {

    private UserService mService;

    private UserController controller;

    @BeforeEach
    public void init() {
        mService = mock(UserService.class);
        controller = new UserController(mService, new AttributeFilter());
    }

    @Test
    public void testGetUser_ThrowsEntityNotFoundException_WhenUserDoesNotExist() {
        when(mService.getUser(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> controller.getUser(1L));
    }

    @Test
    public void testGetUser_ReturnsUserDto_WhenUserExists() {
        User user = new User(
            1L,
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

        doReturn(user).when(mService).getUser(1L);

        UserDto dto = controller.getUser(1L);

        UserDto expected = new UserDto(
            1L,
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            "IMAGE_URL",
            new UserStatusDto(1L),
            new UserSalutationDto(2L),
            List.of(new UserRoleDto(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1            
        );
        
        assertEquals(expected, dto);
    }

    @Test
    public void testGetUsers_ReturnsUserPageDtoMatchedRequestParameters_WhenAttributeValuesProvided() {
        Page<User> userPage = new PageImpl<>(List.of(
            new User(
                1L,
                "displayName",
                "firstName",
                "lastName",
                "email",
                "phoneNumber",
                "imageUrl",
                new UserStatus(1L),
                new UserSalutation(2L),
                List.of(new UserRole(3L)),
                LocalDateTime.of(1999, 12, 12, 0, 0),
                LocalDateTime.of(2000, 12, 12, 0, 0),
                1
            )
        ));
        doReturn(userPage).when(mService).getUsers(
            Set.of(1L),
            Set.of(2L),
            Set.of("userName"),
            Set.of("displayName"),
            Set.of("email"),
            Set.of("phoneNumber"),
            Set.of(10L),
            Set.of(20L),
            Set.of("role"),
            1,
            100,
            Set.of("username"),
            true
        );

        final PageDto<UserDto> pageDto = controller.getUsers(
            Set.of(1L),
            Set.of(2L),
            Set.of("userName"),
            Set.of("displayName"),
            Set.of("email"),
            Set.of("phoneNumber"),
            Set.of(10L),
            Set.of(20L),
            Set.of("role"),
            Set.of("username"),
            true,
            1,
            100
        );
        
        PageDto<UserDto> expected = new PageDto<UserDto>(
            List.of(new UserDto(
                1L,
                "displayName",
                "firstName",
                "lastName",
                "email",
                "phoneNumber",
                "imageUrl",
                new UserStatusDto(1L),
                new UserSalutationDto(2L),
                List.of(new UserRoleDto(3L)),
                LocalDateTime.of(1999, 12, 12, 0, 0),
                LocalDateTime.of(2000, 12, 12, 0, 0),
                1
            )),
            1,
            1
        );

        assertEquals(expected, pageDto);
    }
    
    @Test
    public void testGetUser_ReturnsUserDto_WhenServiceReturnUser() {
        User user = new User(
            1L,
            "displayName",
            "firstName",
            "lastName",
            "email",
            "phoneNumber",
            "imageUrl",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 12, 12, 0, 0),
            LocalDateTime.of(2000, 12, 12, 0, 0),
            1
        );
        doReturn(user).when(mService).getUser(1L);

        UserDto dto = controller.getUser(1L);

        UserDto expected = new UserDto(
            1L,
            "displayName",
            "firstName",
            "lastName",
            "email",
            "phoneNumber",
            "imageUrl",
            new UserStatusDto(1L),
            new UserSalutationDto(2L),
            List.of(new UserRoleDto(3L)),
            LocalDateTime.of(1999, 12, 12, 0, 0),
            LocalDateTime.of(2000, 12, 12, 0, 0),
            1
        );
        assertEquals(expected, dto);
    }
    
    @Test
    public void testGetUser_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
        doReturn(null).when(mService).getUser(1L);
        assertThrows(EntityNotFoundException.class, () -> controller.getUser(1L));
    }
    
    @Test
    public void testAddUser_

    @Test
    public void testAddUser_SavesUserAndReturnsUserDto_WhenAddUserDtoIsProvided() {
        final AddUserDto addUserDto = createAddUserDto();
        final Long userId = 1L;
        when(mService.addUser(any(User.class))).thenReturn(createUserFromAddUserDto(userId, addUserDto));

        final UserDto addedUserDto = controller.addUser(addUserDto);

        assertEquals(userId, addedUserDto.getId());
        assertAddUserValuesAgainstAddedUser(addUserDto, addedUserDto);
    }

    @Test
    public void testPutUser_UpdateUserAndReturnsUserDto_WhenUpdateUserDtoIsProvidedForGivenUser() {
        final Long userId = 1L;
        final UpdateUserDto updateUserDto = createUpdateUserDto();

        when(mService.putUser(anyLong(), any(User.class))).thenReturn(createUserFromUpdateUserDto(userId, updateUserDto));

        final UserDto updatedUserDto = controller.putUser(userId, updateUserDto);

        assertEquals(userId, updatedUserDto.getId());
        assertUpdateUserValuesAgainstUpdatedUser(updateUserDto, updatedUserDto);
    }

    @Test
    public void testPatchUser_UpdateUserAndReturnsUserDto_WhenUpdateUserDtoIsProvidedForGivenUser() {
        final Long userId = 1L;
        final UpdateUserDto patchUserDto = createEmailAndDisplayNamePatchUserDto();

        when(mService.patchUser(anyLong(), any(User.class))).thenReturn(createUserFromUpdateUserDto(userId, patchUserDto));

        final UserDto updatedUserDto = controller.patchUser(userId, patchUserDto);

        assertEquals(userId, updatedUserDto.getId());
        assertEquals(patchUserDto.getDisplayName(), updatedUserDto.getDisplayName());
        assertEquals(patchUserDto.getEmail(), updatedUserDto.getEmail());
        assertEquals(patchUserDto.getVersion() + 1, updatedUserDto.getVersion());
    }

    @Test
    public void testDeleteUser_DeletesUser_WhenUserIdIsProvided() {
        final Long userId = 1L;
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing().when(mService).deleteUser(userIdCaptor.capture());

        controller.deleteUser(userId);

        assertEquals(userId, userIdCaptor.getValue());
    }

    private void assertAddUserValuesAgainstAddedUser(final AddUserDto addUserDto, final UserDto addedUserDto) {
        assertEquals(addUserDto.getUserName(), addedUserDto.getUserName());
        assertEquals(addUserDto.getDisplayName(), addedUserDto.getDisplayName());
        assertEquals(addUserDto.getFirstName(), addedUserDto.getFirstName());
        assertEquals(addUserDto.getLastName(), addedUserDto.getLastName());
        assertEquals(addUserDto.getEmail(), addedUserDto.getEmail());
        assertEquals(addUserDto.getPhoneNumber(), addedUserDto.getPhoneNumber());
        assertEquals(addUserDto.getStatus().getName(), addedUserDto.getStatus().getName());
        assertEquals(addUserDto.getSalutation().getName(), addedUserDto.getSalutation().getName());
        assertEquals(addUserDto.getRoles().size(), addedUserDto.getRoles().size());
        assertEquals(addUserDto.getRoles().get(0).getUserRoleType().getName(), addedUserDto.getRoles().get(0).getUserRoleType().getName());
    }

    private void assertUpdateUserValuesAgainstUpdatedUser(final UpdateUserDto addUserDto, final UserDto addedUserDto) {
        assertEquals(addUserDto.getDisplayName(), addedUserDto.getDisplayName());
        assertEquals(addUserDto.getFirstName(), addedUserDto.getFirstName());
        assertEquals(addUserDto.getLastName(), addedUserDto.getLastName());
        assertEquals(addUserDto.getEmail(), addedUserDto.getEmail());
        assertEquals(addUserDto.getPhoneNumber(), addedUserDto.getPhoneNumber());
        assertEquals(addUserDto.getStatus().getName(), addedUserDto.getStatus().getName());
        assertEquals(addUserDto.getSalutation().getName(), addedUserDto.getSalutation().getName());
        assertEquals(addUserDto.getRoles().size(), addedUserDto.getRoles().size());
        assertEquals(addUserDto.getRoles().get(0).getUserRoleType().getName(), addedUserDto.getRoles().get(0).getUserRoleType().getName());
        assertEquals(addUserDto.getVersion() + 1, addedUserDto.getVersion());
    }

    private UserRole createUserRole(final String roleName) {
        final UserRole userRole = new UserRole();
        userRole.setUserRoleType(createFixedType(UserRoleType::new, roleName));
        return userRole;
    }

    private <T extends FixedTypeEntity> T createFixedType(final Supplier<T> fixedTypeSupplier, final String name) {
        final T fixedType = fixedTypeSupplier.get();
        fixedType.setName(name);
        return fixedType;
    }

    private User createUserFromAddUserDto(final Long userId, final AddUserDto addUserDto) {
        final User user = UserMapper.INSTANCE.fromDto(addUserDto);
        user.setId(userId);
        return user;
    }

    private User createUserFromUpdateUserDto(final Long userId, final UpdateUserDto updateUserDto) {
        final User user = UserMapper.INSTANCE.fromDto(updateUserDto);
        user.setId(userId);
        user.setVersion(updateUserDto.getVersion() + 1);
        return user;
    }

    private AddUserDto createAddUserDto() {
        final AddUserDto addUser = new AddUserDto();
        addUser.setUserName("testUserName");
        addUser.setDisplayName("testDisplayName");
        addUser.setFirstName("testFirstName");
        addUser.setLastName("testLastName");
        addUser.setEmail("testEmail");
        addUser.setPhoneNumber("testPhoneNumber");
        addUser.setImageUrl("testImageUrl");
        addUser.setStatus(createFixedTypeDto("testStatus"));
        addUser.setSalutation(createFixedTypeDto("testSalutation"));
        addUser.setRoles(Collections.singletonList(createAddUserRoleDto("testRoleName")));
        return addUser;
    }

    private AddUserRoleDto createAddUserRoleDto(final String roleName) {
        final AddUserRoleDto addUserRoleDto = new AddUserRoleDto();
        addUserRoleDto.setUserRoleType(createFixedTypeDto(roleName));
        return addUserRoleDto;
    }

    private FixedTypeDto createFixedTypeDto(final String name) {
        final FixedTypeDto fixedType = new FixedTypeDto();
        fixedType.setName(name);
        return fixedType;
    }

    private UpdateUserDto createEmailAndDisplayNamePatchUserDto() {
        final UpdateUserDto patchUserDto = new UpdateUserDto();
        patchUserDto.setDisplayName("patchingTestDisplayName");
        patchUserDto.setEmail("patchingTestEmail");
        patchUserDto.setVersion(1);
        return patchUserDto;
    }

    private UpdateUserDto createUpdateUserDto() {
        final UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setDisplayName("updatingTestDisplayName");
        updateUserDto.setFirstName("updatingTestFirstName");
        updateUserDto.setLastName("updatingTestLastName");
        updateUserDto.setEmail("updatingTestEmail");
        updateUserDto.setPhoneNumber("updatingTestPhoneNumber");
        updateUserDto.setImageUrl("updatingTestImageUrl");
        updateUserDto.setStatus(createFixedTypeDto("updatingTestStatus"));
        updateUserDto.setSalutation(createFixedTypeDto("updatingTestSalutation"));
        updateUserDto.setRoles(Collections.singletonList(createAddUserRoleDto("updatingTestRoleName")));
        updateUserDto.setVersion(0);
        return updateUserDto;
    }
}
