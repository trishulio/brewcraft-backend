package io.company.brewcraft.controller;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.common.FixedTypeDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.AddUserRoleDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.common.FixedTypeEntity;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.user.UserMapper;
import io.company.brewcraft.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testGetUser_ThrowsEntityNotFoundException_WhenUserDoesNotExist() {
        final Long userId = 1L;
        when(userService.getUser(userId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> userController.getUser(userId));
    }

    @Test
    public void testGetUser_ReturnsUserDto_WhenUserExists() {
        final Long userId = 1L;
        final User user = createUser(userId);
        when(userService.getUser(userId)).thenReturn(user);

        final UserDto userDto = userController.getUser(userId);

        assertEquals(userId, userDto.getId());
    }

    @Test
    public void testGetUsers_ReturnsUserPageDtoMatchedRequestParameters_WhenAttributeValuesProvided() {
        final Set<Long> ids = Collections.singleton(1L);
        final Set<Long> excludeIds = Collections.singleton(2L);
        final Set<String> userNames = Collections.singleton("testUserName");
        final Set<String> displayNames = Collections.singleton("testDisplayName");
        final Set<String> emails = Collections.singleton("testEmail");
        final Set<String> phoneNumbers = Collections.singleton("testPhoneNumber");
        final Set<String> status = Collections.singleton("testStatus");
        final Set<String> salutations = Collections.singleton("testSalutation");
        final Set<String> roles = Collections.singleton("testRole");
        final int page = 1;
        final int size = 100;
        final Set<String> sort = Collections.singleton("testUserName");
        final boolean orderAscending = true;

        final User matchedUser = createUser(ids.iterator().next());
        Page<User> userPage = new PageImpl<>(Collections.singletonList(matchedUser));
        when(userService.getUsers(ids, excludeIds, userNames, displayNames, emails, phoneNumbers, status, salutations, roles, page, size, sort, orderAscending)).thenReturn(userPage);

        final PageDto<UserDto> usersPage = userController.getUsers(ids, excludeIds, userNames, displayNames, emails, phoneNumbers, status, salutations, roles, page, size, sort, orderAscending);
        assertEquals(page, usersPage.getTotalPages());

        final List<UserDto> users = usersPage.getContent();
        assertEquals(1, users.size());

        final UserDto matchedUserDto = users.get(0);
        assertEquals(ids.iterator().next(), matchedUserDto.getId());
        assertEquals(userNames.iterator().next(), matchedUserDto.getUserName());
        assertEquals(displayNames.iterator().next(), matchedUserDto.getDisplayName());
        assertEquals(emails.iterator().next(), matchedUserDto.getEmail());
        assertEquals(phoneNumbers.iterator().next(), matchedUserDto.getPhoneNumber());
        assertEquals(salutations.iterator().next(), matchedUserDto.getSalutation().getName());
        assertEquals(status.iterator().next(), matchedUserDto.getStatus().getName());
        assertEquals(roles.iterator().next(), matchedUserDto.getRoles().get(0).getUserRoleType().getName());
    }

    @Test
    public void testAddUser_SavesUserAndReturnsUserDto_WhenAddUserDtoIsProvided() {
        final AddUserDto addUserDto = createAddUserDto();
        final Long userId = 1L;
        when(userService.addUser(any(User.class))).thenReturn(createUserFromAddUserDto(userId, addUserDto));

        final UserDto addedUserDto = userController.addUser(addUserDto);

        assertEquals(userId, addedUserDto.getId());
        assertAddUserValuesAgainstAddedUser(addUserDto, addedUserDto);
    }

    @Test
    public void testPutUser_UpdateUserAndReturnsUserDto_WhenUpdateUserDtoIsProvidedForGivenUser() {
        final Long userId = 1L;
        final UpdateUserDto updateUserDto = createUpdateUserDto();

        when(userService.putUser(anyLong(), any(User.class))).thenReturn(createUserFromUpdateUserDto(userId, updateUserDto));

        final UserDto updatedUserDto = userController.putUser(userId, updateUserDto);

        assertEquals(userId, updatedUserDto.getId());
        assertUpdateUserValuesAgainstUpdatedUser(updateUserDto, updatedUserDto);
    }

    @Test
    public void testPatchUser_UpdateUserAndReturnsUserDto_WhenUpdateUserDtoIsProvidedForGivenUser() {
        final Long userId = 1L;
        final UpdateUserDto patchUserDto = createEmailAndDisplayNamePatchUserDto();

        when(userService.patchUser(anyLong(), any(User.class))).thenReturn(createUserFromUpdateUserDto(userId, patchUserDto));

        final UserDto updatedUserDto = userController.patchUser(userId, patchUserDto);

        assertEquals(userId, updatedUserDto.getId());
        assertEquals(patchUserDto.getDisplayName(), updatedUserDto.getDisplayName());
        assertEquals(patchUserDto.getEmail(), updatedUserDto.getEmail());
        assertEquals(patchUserDto.getVersion() + 1, updatedUserDto.getVersion());
    }

    @Test
    public void testDeleteUser_DeletesUser_WhenUserIdIsProvided() {
        final Long userId = 1L;
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing().when(userService).deleteUser(userIdCaptor.capture());

        userController.deleteUser(userId);

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

    private User createUser(final Long userId) {
        final User user = new User();
        user.setId(userId);
        user.setUserName("testUserName");
        user.setDisplayName("testDisplayName");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail");
        user.setPhoneNumber("testPhoneNumber");
        user.setImageUrl("testImageUrl");
        user.setStatus(createFixedType(UserStatus::new, "testStatus"));
        user.setSalutation(createFixedType(UserSalutation::new, "testSalutation"));
        user.setRoles(Collections.singletonList(createUserRole("testRole")));
        return user;
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
