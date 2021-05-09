package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.UserRoleTypeDto;
import io.company.brewcraft.model.user.UserRoleType;

@Mapper
public interface UserRoleTypeMapper {
    UserRoleTypeMapper INSTANCE = Mappers.getMapper(UserRoleTypeMapper.class);

    @Mappings({
        @Mapping(target = UserRoleType.ATTR_NAME, ignore = true),
        @Mapping(target = UserRoleType.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = UserRoleType.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = UserRoleType.ATTR_VERSION, ignore = true)
    })
    UserRoleType fromDto(Long id);

    UserRoleType fromDto(UserRoleTypeDto roleTypeDto);

    UserRoleTypeDto toDto(UserRoleType userRoleType);
}
