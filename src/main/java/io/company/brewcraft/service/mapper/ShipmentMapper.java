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
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "lastUpdated", ignore = true),
    })
    Shipment fromDto(UpdateShipmentDto dto);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    Shipment fromDto(AddShipmentDto dto);

    ShipmentDto toDto(Shipment shipment);
}
