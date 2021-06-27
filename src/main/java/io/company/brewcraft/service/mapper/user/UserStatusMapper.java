package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.UserStatusDto;
import io.company.brewcraft.model.user.UserStatus;

@Mapper
public interface UserStatusMapper {
    UserStatusMapper INSTANCE = Mappers.getMapper(UserStatusMapper.class);

    @Mappings({
        @Mapping(target = UserStatus.ATTR_NAME, ignore = true),
        @Mapping(target = UserStatus.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = UserStatus.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = UserStatus.ATTR_VERSION, ignore = true)
    })
    UserStatus fromDto(Long id);

    UserStatusDto toDto(UserStatus status);
}
