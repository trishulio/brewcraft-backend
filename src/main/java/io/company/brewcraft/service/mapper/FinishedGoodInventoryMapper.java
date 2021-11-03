package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.model.FinishedGoodInventory;

@Mapper(uses = { SkuMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class})
public interface FinishedGoodInventoryMapper {

    FinishedGoodInventoryMapper INSTANCE = Mappers.getMapper(FinishedGoodInventoryMapper.class);

    FinishedGoodInventoryDto toDto(FinishedGoodInventory finishedGood);

    FinishedGoodInventory fromDto(FinishedGoodInventoryDto dto);
}
