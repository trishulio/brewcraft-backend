package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.TaxRateDto;
import io.company.brewcraft.model.TaxRate;

@Mapper
public interface TaxRateMapper {
    final TaxRateMapper INSTANCE = Mappers.getMapper(TaxRateMapper.class);

    TaxRateDto toDto(TaxRate taxRate);

    TaxRate fromDto(TaxRateDto dto);
}
