package io.company.brewcraft.controller;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.user.UserMapper;
import io.company.brewcraft.service.user.UserService;
import io.company.brewcraft.util.validator.Validator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<UserDto> getUsers(@RequestParam(required = false) Set<Long> ids,
                                     @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
                                     @RequestParam(required = false, name = "user_names") Set<String> userNames,
                                     @RequestParam(required = false, name = "display_names") Set<String> displayNames,
                                     @RequestParam(required = false, name = "emails") Set<String> emails,
                                     @RequestParam(required = false, name = "phone_numbers") Set<String> phoneNumbers,
                                     @RequestParam(required = false, name = "status") Set<String> status,
                                     @RequestParam(required = false, name = "salutations") Set<String> salutations,
                                     @RequestParam(required = false, name = "roles") Set<String> roles,
                                     @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
                                     @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true", name = "order_asc") boolean orderAscending) {

        Page<User> userPage = userService.getUsers(ids, excludeIds, userNames, displayNames, emails, phoneNumbers, status, salutations, roles, page, size, sort, orderAscending);

        List<UserDto> userList = userPage.stream()
                .map(UserMapper.INSTANCE::toDto).collect(Collectors.toList());

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
        User user = UserMapper.INSTANCE.fromDto(userDto);
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
