package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.MaterialPortion;

@Mapper(uses = { MaterialLotMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MixtureMapper.class, MoneyMapper.class})
public interface MaterialPortionMapper {

    MaterialPortionMapper INSTANCE = Mappers.getMapper(MaterialPortionMapper.class);
    
    @Mapping(target = "mixtureId", source = "mixture.id")
    MaterialPortionDto toDto(MaterialPortion materialPortion);

    @Mapping(target = MaterialPortion.ATTR_ID, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_VERSION, ignore = true)
    @Mapping(target = "materialLot", source = "materialLotId")
    @Mapping(target = "mixture", source = "mixtureId")
    MaterialPortion fromDto(AddMaterialPortionDto dto);

    @Mapping(target = MaterialPortion.ATTR_ID, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialPortion.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "materialLot", source = "materialLotId")
    @Mapping(target = "mixture", source = "mixtureId")
    MaterialPortion fromDto(UpdateMaterialPortionDto dto);

}
