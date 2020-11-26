package io.company.brewcraft.service.mapper;

import org.joda.money.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.MoneyDto;

@Mapper
public interface MoneyMapper {
    MoneyMapper INSTANCE = Mappers.getMapper(MoneyMapper.class);
    
    
    @Mappings({
        @Mapping(source = "money.amount", target = "amount"),
        @Mapping(source = "money.currencyUnit.code", target = "currency")
    })
    MoneyDto toDto(Money money);

    default Money fromDto(MoneyDto dto) {
        return Money.parse(String.format("%s %s", dto.getCurrency(), dto.getAmount()));
    }
}
