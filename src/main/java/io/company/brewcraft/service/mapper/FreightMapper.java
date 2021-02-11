package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.model.FreightEntity;
import io.company.brewcraft.pojo.Freight;

@Mapper(uses = { MoneyMapper.class })
public interface FreightMapper {
    FreightMapper INSTANCE = Mappers.getMapper(FreightMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    FreightEntity toEntity(Freight freight);

    FreightDto toDto(Freight freight);

    Freight fromEntity(FreightEntity entity);

    Freight fromDto(FreightDto dto);
}
