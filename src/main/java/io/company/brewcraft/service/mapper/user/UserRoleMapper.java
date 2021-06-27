package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.model.user.UserRole;

@Mapper
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    @Mappings({
        @Mapping(target = UserRole.ATTR_NAME, ignore = true),
        @Mapping(target = UserRole.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = UserRole.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = UserRole.ATTR_VERSION, ignore = true)
    })
    UserRole fromDto(Long id);

    UserRoleDto toDto(UserRole userRole);
}
