package io.company.brewcraft.service.mapper.user;

import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserStatusMapper.class, UserSalutationMapper.class, UserRoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "lastUpdated", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    User fromDto(AddUserDto addUserDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userName", ignore = true),
            @Mapping(target = "lastUpdated", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    User fromDto(UpdateUserDto updateUserDto);

    UserDto toDto(User user);
}
