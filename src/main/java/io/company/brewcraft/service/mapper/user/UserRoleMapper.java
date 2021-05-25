package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.AddUserRoleDto;
import io.company.brewcraft.dto.user.UpdateUserRoleDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.model.user.UserRole;

@Mapper(uses = { UserRoleTypeMapper.class, UserMapper.class })
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleDto toDto(UserRole userRole);

    @Mappings({
        @Mapping(target = UserRole.ATTR_USER, ignore = true),        
    })
    UserRole fromDto(UserRoleDto dto);

    @Mappings({
        @Mapping(target = UserRole.ATTR_USER, ignore = true),
        @Mapping(target = UserRole.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = UserRole.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = UserRole.ATTR_ROLE_TYPE, source = "roleTypeId")
    })
    UserRole fromDto(UpdateUserRoleDto dto);

    @Mappings({
        @Mapping(target = UserRole.ATTR_ID, ignore = true),
        @Mapping(target = UserRole.ATTR_USER, ignore = true),
        @Mapping(target = UserRole.ATTR_VERSION, ignore = true),
        @Mapping(target = UserRole.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = UserRole.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = UserRole.ATTR_ROLE_TYPE, source = "roleTypeId")
    })
    UserRole fromDto(AddUserRoleDto addUserRoleDto);
}
