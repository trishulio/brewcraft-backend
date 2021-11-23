package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.dto.user.UserSalutationDto;
import io.company.brewcraft.dto.user.UserStatusDto;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.service.exception.EntityNotFoundException;
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

        doReturn(user).when(mService).getUser(1L);

        UserDto dto = controller.getUser(1L);

        UserDto expected = new UserDto(
            1L,
            "USER_NAME",
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
                "userName",
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
            new TreeSet<>(List.of("username")),
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
            new TreeSet<>(List.of("username")),
            true,
            1,
            100
        );

        PageDto<UserDto> expected = new PageDto<UserDto>(
            List.of(new UserDto(
                1L,
                "userName",
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
            "userName",
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
            "userName",
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
    public void testAddUser_ReturnsUserDtoFromService_WhenInputArgIsNotNull() {
        doAnswer(inv -> inv.getArgument(0, User.class)).when(mService).addUser(any(User.class));
        AddUserDto additionDto = new AddUserDto(
            "displayName",
            "firstName",
            "lastName",
            "email",
            1L,
            2L,
            "phoneNumber",
            "imageUrl",
            List.of(10L)
        );

        UserDto dto = controller.addUser(additionDto);

        UserDto expected = new UserDto(
            null,
            null,
            "displayName",
            "firstName",
            "lastName",
            "email",
            "phoneNumber",
            "imageUrl",
            new UserStatusDto(1L),
            new UserSalutationDto(2L),
            List.of(new UserRoleDto(10L)),
            null,
            null,
            null
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testPutUser_ReturnsUserDtoFromService_WhenInputArgIsNotNull() {
        doAnswer(inv -> inv.getArgument(1, User.class)).when(mService).putUser(eq(1L), any(UpdateUser.class));
        UpdateUserDto updateDto = new UpdateUserDto(
            "displayName",
            "firstName",
            "lastName",
            1L,
            2L,
            "phoneNumber",
            "imageUrl",
            List.of(10L),
            1
        );

        UserDto dto = controller.putUser(1L, updateDto);

        UserDto expected = new UserDto(
            null,
            null,
            "displayName",
            "firstName",
            "lastName",
            null,
            "phoneNumber",
            "imageUrl",
            new UserStatusDto(1L),
            new UserSalutationDto(2L),
            List.of(new UserRoleDto(10L)),
            null,
            null,
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testPatchUser_ReturnsUserDtoFromService_WhenInputArgIsNotNull() {
        doAnswer(inv -> inv.getArgument(1, User.class)).when(mService).patchUser(eq(1L), any(UpdateUser.class));
        UpdateUserDto updateDto = new UpdateUserDto(
            "displayName",
            "firstName",
            "lastName",
            1L,
            2L,
            "phoneNumber",
            "imageUrl",
            List.of(10L),
            1
        );

        UserDto dto = controller.patchUser(1L, updateDto);

        UserDto expected = new UserDto(
            null,
            null,
            "displayName",
            "firstName",
            "lastName",
            null,
            "phoneNumber",
            "imageUrl",
            new UserStatusDto(1L),
            new UserSalutationDto(2L),
            List.of(new UserRoleDto(10L)),
            null,
            null,
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testDeleteUser_DeletesUser_WhenUserIdIsProvided() {
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing().when(mService).deleteUser(userIdCaptor.capture());

        controller.deleteUser(1L);

        assertEquals(1L, userIdCaptor.getValue());
    }
}
