package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.BaseUser;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.impl.user.UserService;

public class UserControllerTest {
    private UserController controller;

    private CrudControllerService<
                Long,
                User,
                BaseUser,
                UpdateUser,
                UserDto,
                AddUserDto,
                UpdateUserDto
            > mCrudController;

    private UserService mService;

    @BeforeEach
    public void init() {
        this.mCrudController = mock(CrudControllerService.class);
        this.mService = mock(UserService.class);
        this.controller = new UserController(mCrudController, mService);
    }

    @Test
    public void testGetAllUser_ReturnsDtosFromController() {
        doReturn(new PageImpl<>(List.of(new User(1L)))).when(mService).getUsers(
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
        doReturn(new PageDto<>(List.of(new UserDto(1L)), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new User(1L))), Set.of(""));

        PageDto<UserDto> page = this.controller.getAllUsers(
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
            100,
            Set.of("")
        );

        PageDto<UserDto> expected = new PageDto<>(List.of(new UserDto(1L)), 1, 1);
        assertEquals(expected, page);
    }

    @Test
    public void testGetUser_ReturnsDtoFromController() {
        doReturn(new UserDto(1L)).when(mCrudController).get(1L, Set.of(""));

        UserDto dto = this.controller.getUser(1L, Set.of(""));

        UserDto expected = new UserDto(1L);
        assertEquals(expected, dto);
    }

    @Test
    public void testDeleteUsers_ReturnsDeleteCountFromController() {
        doReturn(1L).when(mCrudController).delete(Set.of(1L));

        assertEquals(1L, this.controller.deleteUsers(Set.of(1L)));
    }

    @Test
    public void testAddUsers_AddsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new UserDto(1L))).when(mCrudController).add(List.of(new AddUserDto()));

        List<UserDto> dtos = this.controller.addUser(List.of(new AddUserDto()));

        assertEquals(List.of(new UserDto(1L)), dtos);
    }

    @Test
    public void testUpdateUsers_PutsToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new UserDto(1L))).when(mCrudController).put(List.of(new UpdateUserDto(1L)));

        List<UserDto> dtos = this.controller.updateUser(List.of(new UpdateUserDto(1L)));

        assertEquals(List.of(new UserDto(1L)), dtos);
    }

    @Test
    public void testPatchUsers_PatchToControllerAndReturnsListOfDtos() {
        doReturn(List.of(new UserDto(1L))).when(mCrudController).patch(List.of(new UpdateUserDto(1L)));

        List<UserDto> dtos = this.controller.patchUser(List.of(new UpdateUserDto(1L)));

        assertEquals(List.of(new UserDto(1L)), dtos);
    }
}
