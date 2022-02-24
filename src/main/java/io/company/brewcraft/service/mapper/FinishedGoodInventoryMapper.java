package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.FinishedGoodInventoryAggregationDto;
import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;

@Mapper(uses = { SkuMapper.class, FinishedGoodLotMaterialPortionMapper.class, FinishedGoodLotMixturePortionMapper.class, FinishedGoodLotFinishedGoodLotPortionMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class})
public interface FinishedGoodInventoryMapper {

    FinishedGoodInventoryMapper INSTANCE = Mappers.getMapper(FinishedGoodInventoryMapper.class);

    FinishedGoodInventoryAggregationDto toDto(FinishedGoodInventoryAggregation finishedGood);

    FinishedGoodInventoryDto toDto(FinishedGoodInventory finishedGood);
}
