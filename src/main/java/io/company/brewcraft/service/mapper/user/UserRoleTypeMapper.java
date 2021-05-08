package io.company.brewcraft.service.mapper.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import io.company.brewcraft.model.user.UserRoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRoleTypeMapper {
    UserRoleTypeMapper INSTANCE = Mappers.getMapper(UserRoleTypeMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    UserRoleType fromDto(FixedTypeDto roleTypeDto);

    FixedTypeDto toDto(UserRoleType userRoleType);
}
