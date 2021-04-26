package io.company.brewcraft.service.mapper.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import io.company.brewcraft.model.user.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserStatusMapper {
    UserStatusMapper INSTANCE = Mappers.getMapper(UserStatusMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    UserStatus fromDto(FixedTypeDto statusDto);

    FixedTypeDto toDto(UserStatus status);
}
