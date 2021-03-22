package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.model.Tax;

@Mapper(uses = { MoneyMapper.class })
public interface TaxMapper {
    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    Tax fromDto(TaxDto dto);

    TaxDto toDto(Tax tax);
}
