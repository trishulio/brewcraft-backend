package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.model.MaterialLot;

@Mapper(uses = {QuantityMapper.class, MaterialMapper.class})
public interface MaterialLotMapper {

    MaterialLotMapper INSTANCE = Mappers.getMapper(MaterialLotMapper.class);

    MaterialLotDto toDto(MaterialLot lot);
    
    @Mappings({
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "shipment", ignore = true),
        @Mapping(target = "material", source = "materialId")
    })
    MaterialLot fromDto(UpdateMaterialLotDto item);
}
