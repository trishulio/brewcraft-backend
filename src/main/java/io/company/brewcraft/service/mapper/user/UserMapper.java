package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.AddUserDto;
import io.company.brewcraft.dto.user.UpdateUserDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.service.mapper.BaseMapper;

@Mapper(uses = { UserStatusMapper.class, UserSalutationMapper.class, UserRoleMapper.class })
public interface UserMapper extends BaseMapper<User, UserDto, AddUserDto, UpdateUserDto> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roleBindings", ignore = true)
    @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = User.ATTR_VERSION, ignore = true)
    @Mapping(target = User.ATTR_STATUS, ignore = true)
    @Mapping(target = User.ATTR_SALUTATION, ignore = true)
    @Mapping(target = User.ATTR_DISPLAY_NAME, ignore = true)
    @Mapping(target = User.ATTR_EMAIL, ignore = true)
    @Mapping(target = User.ATTR_USER_NAME, ignore = true)
    @Mapping(target = User.ATTR_FIRST_NAME, ignore = true)
    @Mapping(target = User.ATTR_LAST_NAME, ignore = true)
    @Mapping(target = User.ATTR_ROLES, ignore = true)
    @Mapping(target = User.ATTR_IMAGE_URL, ignore = true)
    @Mapping(target = User.ATTR_PHONE_NUMBER, ignore = true)
    User fromDto(Long id);

    @Override
    @Mapping(target = "roleBindings", ignore = true)
    @Mapping(target = User.ATTR_ID, ignore = true)
    @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = User.ATTR_VERSION, ignore = true)
    @Mapping(target = User.ATTR_STATUS, source = "statusId")
    @Mapping(target = User.ATTR_SALUTATION, source = "salutationId")
    @Mapping(target = User.ATTR_ROLES, source = "roleIds")
    User fromAddDto(AddUserDto addUserDto);

    @Override
    @Mapping(target = "roleBindings", ignore = true)
    @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = User.ATTR_EMAIL, ignore = true)
    @Mapping(target = User.ATTR_STATUS, source = "statusId")
    @Mapping(target = User.ATTR_SALUTATION, source = "salutationId")
    @Mapping(target = User.ATTR_ROLES, source = "roleIds")
    User fromUpdateDto(UpdateUserDto updateUserDto);

    @Override
    UserDto toDto(User user);
}
