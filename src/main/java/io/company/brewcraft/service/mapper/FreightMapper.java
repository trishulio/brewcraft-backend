package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.model.Freight;

@Mapper(uses = { MoneyMapper.class })
public interface FreightMapper {
    FreightMapper INSTANCE = Mappers.getMapper(FreightMapper.class);

    FreightDto toDto(Freight freight);

    Freight fromDto(FreightDto dto);
}
