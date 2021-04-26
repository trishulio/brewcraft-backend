package io.company.brewcraft.service.mapper.user;

import io.company.brewcraft.dto.common.FixedTypeDto;
import io.company.brewcraft.model.user.UserSalutation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserSalutationMapper {
    UserSalutationMapper INSTANCE = Mappers.getMapper(UserSalutationMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    UserSalutation fromDto(FixedTypeDto salutationDto);

    FixedTypeDto toDto(UserSalutation salutation);
}
