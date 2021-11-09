package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixtureMaterialPortionDto;
import io.company.brewcraft.dto.MixtureMaterialPortionDto;
import io.company.brewcraft.dto.UpdateMixtureMaterialPortionDto;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.MixtureMaterialPortion;

@Mapper(uses = { MaterialLotMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MixtureMapper.class, MoneyMapper.class})
public interface MixtureMaterialPortionMapper {

    MixtureMaterialPortionMapper INSTANCE = Mappers.getMapper(MixtureMaterialPortionMapper.class);

    MixtureMaterialPortionDto toDto(MixtureMaterialPortion materialPortion);

    @Mapping(target = MaterialPortion.ATTR_ID, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_VERSION, ignore = true)
    @Mapping(target = "materialLot", source = "materialLotId")
    @Mapping(target = "mixture", source = "mixtureId")
    MixtureMaterialPortion fromDto(AddMixtureMaterialPortionDto dto);

    @Mapping(target = MaterialPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "materialLot", source = "materialLotId")
    @Mapping(target = "mixture", source = "mixtureId")
    MixtureMaterialPortion fromDto(UpdateMixtureMaterialPortionDto dto);

}
