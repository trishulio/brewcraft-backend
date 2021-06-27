package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.User;

@Mapper(uses = { UserStatusMapper.class, UserSalutationMapper.class, UserRoleMapper.class })
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(target = "roleBindings", ignore = true),
        @Mapping(target = User.ATTR_ID, ignore = true),
        @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = User.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = User.ATTR_VERSION, ignore = true),
        @Mapping(target = User.ATTR_STATUS, source = "statusId"),
        @Mapping(target = User.ATTR_SALUTATION, source = "salutationId"),
        @Mapping(target = User.ATTR_ROLES, source = "roleIds")
    })
    User fromDto(AddUserDto addUserDto);

    @Mappings({
        @Mapping(target = "roleBindings", ignore = true),
        @Mapping(target = User.ATTR_USER_NAME, ignore = true),
        @Mapping(target = User.ATTR_ID, ignore = true),
        @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = User.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = User.ATTR_STATUS, source = "statusId"),
        @Mapping(target = User.ATTR_SALUTATION, source = "salutationId"),
        @Mapping(target = User.ATTR_ROLES, source = "roleIds")
    })
    User fromDto(UpdateUserDto updateUserDto);

    UserDto toDto(User user);
}
