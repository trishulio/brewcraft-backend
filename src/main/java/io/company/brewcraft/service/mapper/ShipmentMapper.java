package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddShipmentDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.model.Shipment;

@Mapper(uses = {MaterialLotMapper.class, ShipmentStatusMapper.class, InvoiceMapper.class, ShipmentStatusMapper.class, QuantityMapper.class})
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    @Mappings({
        @Mapping(target = Shipment.ATTR_ID, ignore = true),
        @Mapping(target = Shipment.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Shipment.ATTR_LAST_UPDATED, ignore = true),
    })
    Shipment fromDto(UpdateShipmentDto dto);

    @Mappings({
        @Mapping(target = Shipment.ATTR_ID, ignore = true),
        @Mapping(target = Shipment.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Shipment.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Shipment.ATTR_VERSION, ignore = true)
    })
    Shipment fromDto(AddShipmentDto dto);

    ShipmentDto toDto(Shipment shipment);
}
