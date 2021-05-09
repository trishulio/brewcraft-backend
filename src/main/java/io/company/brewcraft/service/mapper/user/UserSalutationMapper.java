package io.company.brewcraft.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.UserSalutationDto;
import io.company.brewcraft.model.user.UserSalutation;

@Mapper
public interface UserSalutationMapper {
    UserSalutationMapper INSTANCE = Mappers.getMapper(UserSalutationMapper.class);

    @Mappings({
        @Mapping(target = UserSalutation.ATTR_TITLE, ignore = true),
        @Mapping(target = UserSalutation.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = UserSalutation.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = UserSalutation.ATTR_VERSION, ignore = true)
    })
    UserSalutation fromDto(Long id);

    UserSalutation fromDto(UserSalutationDto salutationDto);

    UserSalutationDto toDto(UserSalutation salutation);
}
