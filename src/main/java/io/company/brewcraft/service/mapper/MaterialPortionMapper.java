package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.model.MaterialPortion;

@Mapper(uses = { MaterialLotMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MixtureMapper.class, MoneyMapper.class})
public interface MaterialPortionMapper {

	MaterialPortionMapper INSTANCE = Mappers.getMapper(MaterialPortionMapper.class);

    MaterialPortion fromDto(AddMaterialPortionDto dto);
    
    //MaterialPortion fromDto(UpdateMaterialPortionDto dto);

    MaterialPortionDto toDto(MaterialPortion materialPortion);

}
