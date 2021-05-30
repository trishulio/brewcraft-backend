package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.dto.user.UserSalutationDto;
import io.company.brewcraft.dto.user.UserStatusDto;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.service.mapper.user.UserMapper;

public class UserMapperTest {

    private UserMapper mapper;
    
    @BeforeEach
    public void init() {
        mapper = UserMapper.INSTANCE;
    }
    
    @Test
    public void testFromDto_ReturnsEntity_WhenAddDtoIsNotNull() {
        AddUserDto dto =  new AddUserDto(
            "userName",
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

        User user = mapper.fromDto(dto);
        
        User expected = new User(
            null,
            "userName",
            "displayName",
            "firstName",
            "lastName",
            "email",
            "phoneNumber",
            "imageUrl",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(10L)),
            null,
            null,
            null
        );
        
        assertEquals(expected, user);
    }
    
    @Test
    public void testFromDto_ReturnsNull_WhenAddDtoIsNull() {
        assertNull(mapper.fromDto((AddUserDto) null));
    }
    
    @Test
    public void testFromDto_ReturnsEntity_WhenUpdateUserDtoIsNotNull() {
        UpdateUserDto dto =  new UpdateUserDto(
            "displayName",
            "firstName",
            "lastName",
            "email",
            1L,
            2L,
            "phoneNumber",
            "imageUrl",
            List.of(10L),
            1
        );

        User user = mapper.fromDto(dto);
        
        User expected = new User(
            null,
            null,
            "displayName",
            "firstName",
            "lastName",
            "email",
            "phoneNumber",
            "imageUrl",
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(10L)),
            null,
            null,
            1
        );
        
        assertEquals(expected, user);
    }
    
    @Test
    public void testFromDto_ReturnsNull_WhenUpdateUserDtoIsNull() {
        assertNull(mapper.fromDto((UpdateUserDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
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

        UserDto dto = mapper.toDto(user);

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
}
