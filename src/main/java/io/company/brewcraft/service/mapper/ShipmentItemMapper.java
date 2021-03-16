package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentItemDto;
import io.company.brewcraft.pojo.ShipmentItem;

@Mapper(uses = {QuantityMapper.class, MaterialMapper.class})
public interface ShipmentItemMapper {

    ShipmentItemMapper INSTANCE = Mappers.getMapper(ShipmentItemMapper.class);

    ShipmentItemDto toDto(ShipmentItem item);

    @Mappings({
        @Mapping(target = "shipment", ignore = true)
    })
    ShipmentItem fromDto(ShipmentItemDto item);
}
