package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.UpdateUser;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.user.UserMapper;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(final UserService userService, AttributeFilter filter) {
        super(filter);
        this.userService = userService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<UserDto> getUsers(
        @RequestParam(required = false) Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "user_names") Set<String> userNames,
        @RequestParam(required = false, name = "display_names") Set<String> displayNames,
        @RequestParam(required = false, name = "emails") Set<String> emails,
        @RequestParam(required = false, name = "phone_numbers") Set<String> phoneNumbers,
        @RequestParam(required = false, name = "status") Set<Long> statusIds,
        @RequestParam(required = false, name = "salutations") Set<Long> salutationIds,
        @RequestParam(required = false, name = "roles") Set<String> roles,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
     ) {
        Page<User> userPage = userService.getUsers(ids, excludeIds, userNames, displayNames, emails, phoneNumbers, statusIds, salutationIds, roles, page, size, sort, orderAscending);

        List<UserDto> userList = userPage
                                 .stream()
                                 .map(UserMapper.INSTANCE::toDto)
                                 .collect(Collectors.toList());
        return new PageDto<>(userList, userPage.getTotalPages(), userPage.getTotalElements());
    }


    @GetMapping(value = "/{userId}", consumes = MediaType.ALL_VALUE)
    public UserDto getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        Validator.assertion(user != null, EntityNotFoundException.class, "User", userId.toString());
        return UserMapper.INSTANCE.toDto(user);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody @NotNull AddUserDto userDto) {
        User user = UserMapper.INSTANCE.fromDto(userDto);
        User addedUser = userService.addUser(user);
        return UserMapper.INSTANCE.toDto(addedUser);
    }

    @PutMapping("/{userId}")
    public UserDto putUser(@PathVariable Long userId, @Valid @RequestBody @NotNull UpdateUserDto userDto) {
        UpdateUser<? extends UpdateUserRole> user = UserMapper.INSTANCE.fromDto(userDto);
        User updatedUser = userService.putUser(userId, user);
        return UserMapper.INSTANCE.toDto(updatedUser);
    }


    @PatchMapping("/{userId}")
    public UserDto patchUser(@PathVariable Long userId, @Valid @RequestBody @NotNull UpdateUserDto userDto) {
        User user = UserMapper.INSTANCE.fromDto(userDto);
        User patchedUser = userService.patchUser(userId, user);
        return UserMapper.INSTANCE.toDto(patchedUser);
    }


    @DeleteMapping(value = "/{userId}", consumes = MediaType.ALL_VALUE)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
