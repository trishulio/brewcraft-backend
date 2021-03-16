package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.pojo.Shipment;

@Mapper(uses = {ShipmentItemMapper.class, ShipmentStatusMapper.class, InvoiceMapper.class})
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    Shipment fromDto(ShipmentDto dto);

    ShipmentDto toDto(Shipment shipment);
}
