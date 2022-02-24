package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixturePortionDto;
import io.company.brewcraft.dto.MixturePortionDto;
import io.company.brewcraft.dto.UpdateMixturePortionDto;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.MixturePortion;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MixtureMapper.class, FinishedGoodLotMapper.class})
public interface FinishedGoodLotMixturePortionMapper {

    FinishedGoodLotMixturePortionMapper INSTANCE = Mappers.getMapper(FinishedGoodLotMixturePortionMapper.class);

    MixturePortionDto toDto(FinishedGoodLotMixturePortion mixturePortion);

    @Mapping(target = MixturePortion.ATTR_ID, ignore = true)
    @Mapping(target = MixturePortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MixturePortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MixturePortion.ATTR_VERSION, ignore = true)
    @Mapping(target = MixturePortion.ATTR_MIXTURE, source = "mixtureId")
    FinishedGoodLotMixturePortion fromDto(AddMixturePortionDto dto);

    @Mapping(target = MixturePortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MixturePortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MixturePortion.ATTR_MIXTURE, source = "mixtureId")
    FinishedGoodLotMixturePortion fromDto(UpdateMixturePortionDto dto);

}
