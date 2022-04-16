package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.model.Tax;

@Mapper(uses = { TaxRateMapper.class })
public interface TaxMapper {
    final TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    TaxDto toDto(Tax tax);

    Tax fromDto(TaxDto dto);
}