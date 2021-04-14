package io.company.brewcraft.controller;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.UserService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.UserMapper;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<UserDto> getUsers(@RequestParam(required = false) Set<Long> ids,
                                     @RequestParam(required = false, name = "display_names") Set<String> displayNames,
                                     @RequestParam(required = false, name = "emails") Set<String> emails,
                                     @RequestParam(required = false, name = "phone_numbers") Set<String> phoneNumbers,
                                     @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
                                     @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true", name = "order_asc") boolean orderAscending) {

        Page<User> userPage = userService.getUsers(ids, displayNames, emails, phoneNumbers, page, size, sort, orderAscending);

        List<UserDto> userList = userPage.stream()
                .map(userMapper::toDto).collect(Collectors.toList());

        return new PageDto<>(userList, userPage.getTotalPages(), userPage.getTotalElements());
    }

    @GetMapping(value = "/{userId}", consumes = MediaType.ALL_VALUE)
    public UserDto getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        Validator validator = new Validator();
        validator.assertion(user != null, EntityNotFoundException.class, "User", userId.toString());
        return userMapper.toDto(user);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody AddUserDto userDto) {
        User user = userMapper.fromDto(userDto);
        User addUser = userService.addUser(user);
        return userMapper.toDto(addUser);
    }

    @PutMapping("/{userId}")
    public UserDto putFacility(@PathVariable Long userId, @Valid @RequestBody UpdateUserDto userDto) {
        User user = userMapper.fromDto(userDto);
        User putUser = userService.putUser(userId, user);
        return userMapper.toDto(putUser);
    }

    @PatchMapping("/{userId}")
    public UserDto patchUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserDto userDto) {
        User user = userMapper.fromDto(userDto);
        User patchUser = userService.patchUser(userId, user);
        return userMapper.toDto(patchUser);
    }

    @DeleteMapping(value = "/{userId}", consumes = MediaType.ALL_VALUE)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
