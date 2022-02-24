package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotDto;
import io.company.brewcraft.model.FinishedGoodLot;

@Mapper(uses = { SkuMapper.class, FinishedGoodLotMaterialPortionMapper.class, FinishedGoodLotMixturePortionMapper.class, FinishedGoodLotFinishedGoodLotPortionMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class})
public interface FinishedGoodLotMapper {

    FinishedGoodLotMapper INSTANCE = Mappers.getMapper(FinishedGoodLotMapper.class);

    FinishedGoodLot fromDto(Long id);

    FinishedGoodLotDto toDto(FinishedGoodLot finishedGoodLot);

    FinishedGoodLot fromDto(FinishedGoodLotDto dto);

    @Mapping(target = FinishedGoodLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = FinishedGoodLot.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = FinishedGoodLot.ATTR_SKU, source = "skuId")
    FinishedGoodLot fromDto(UpdateFinishedGoodLotDto dto);

    @Mapping(target = FinishedGoodLot.ATTR_ID, ignore = true)
    @Mapping(target = FinishedGoodLot.ATTR_VERSION, ignore = true)
    @Mapping(target = FinishedGoodLot.ATTR_SKU, source = "skuId")
    @Mapping(target = FinishedGoodLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = FinishedGoodLot.ATTR_CREATED_AT, ignore = true)
    FinishedGoodLot fromDto(AddFinishedGoodLotDto dto);

}
