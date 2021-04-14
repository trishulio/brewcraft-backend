package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentItemDto;
import io.company.brewcraft.dto.UpdateShipmentItemDto;
import io.company.brewcraft.model.ShipmentItem;

@Mapper(uses = {QuantityMapper.class, MaterialMapper.class})
public interface ShipmentItemMapper {

    ShipmentItemMapper INSTANCE = Mappers.getMapper(ShipmentItemMapper.class);

    ShipmentItemDto toDto(ShipmentItem item);
    
    @Mappings({
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "shipment", ignore = true),
        @Mapping(target = "material", source = "materialId")
    })
    ShipmentItem fromDto(UpdateShipmentItemDto item);
}
