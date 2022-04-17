package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AmountDto;
import io.company.brewcraft.model.Amount;

@Mapper(uses = { TaxMapper.class, MoneyMapper.class })
public interface AmountMapper {
    final AmountMapper INSTANCE = Mappers.getMapper(AmountMapper.class);

    AmountDto toDto(Amount amount);

    Amount fromDto(AmountDto dto);
}
