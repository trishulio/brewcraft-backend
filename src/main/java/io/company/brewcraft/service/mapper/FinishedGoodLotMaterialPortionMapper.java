package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.MaterialPortion;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MaterialLotMapper.class, FinishedGoodLotMapper.class})
public interface FinishedGoodLotMaterialPortionMapper {

    FinishedGoodLotMaterialPortionMapper INSTANCE = Mappers.getMapper(FinishedGoodLotMaterialPortionMapper.class);

    MaterialPortionDto toDto(FinishedGoodLotMaterialPortion materialPortion);

    @Mapping(target = MaterialPortion.ATTR_ID, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_VERSION, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_MATERIAL_LOT, source = "materialLotId")
    FinishedGoodLotMaterialPortion fromDto(AddMaterialPortionDto dto);

    @Mapping(target = MaterialPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_MATERIAL_LOT, source = "materialLotId")
    FinishedGoodLotMaterialPortion fromDto(UpdateMaterialPortionDto dto);

}
