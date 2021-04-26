package io.company.brewcraft.service.mapper.user;

import io.company.brewcraft.dto.user.AddUserRoleDto;
import io.company.brewcraft.dto.user.UserRoleDto;
import io.company.brewcraft.model.user.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserRoleTypeMapper.class})
public interface UserRoleMapper {

    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "lastUpdated", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    UserRole fromDto(AddUserRoleDto addUserRoleDto);

    UserRoleDto fromDto(UserRole userRole);
}
