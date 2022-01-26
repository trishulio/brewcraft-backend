package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFinishedGoodLotPortionDto;
import io.company.brewcraft.dto.FinishedGoodLotPortionDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotPortionDto;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotPortion;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class})
public interface FinishedGoodLotFinishedGoodLotPortionMapper {

    FinishedGoodLotFinishedGoodLotPortionMapper INSTANCE = Mappers.getMapper(FinishedGoodLotFinishedGoodLotPortionMapper.class);

    FinishedGoodLot fromDto(Long id);

    FinishedGoodLotPortionDto toDto(FinishedGoodLotFinishedGoodLotPortion finishedGoodLotFinishedGoodLotPortion);

    @Mapping(target = FinishedGoodLotPortion.ATTR_ID, ignore = true)
    @Mapping(target = FinishedGoodLotPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = FinishedGoodLotPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = FinishedGoodLotPortion.ATTR_VERSION, ignore = true)
    @Mapping(target = "finishedGoodLot", source = "finishedGoodLotId")
    FinishedGoodLotFinishedGoodLotPortion fromDto(AddFinishedGoodLotPortionDto dto);

    @Mapping(target = FinishedGoodLotPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = FinishedGoodLotPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "finishedGoodLot", source = "finishedGoodLotId")
    FinishedGoodLotFinishedGoodLotPortion fromDto(UpdateFinishedGoodLotPortionDto dto);

}
