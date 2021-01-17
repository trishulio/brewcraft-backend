package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.model.ShipmentEntity;
import io.company.brewcraft.pojo.Shipment;

@Mapper
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    Shipment fromDto(ShipmentDto dto);

    Shipment fromEntity(ShipmentEntity entity);

    ShipmentDto toDto(Shipment shipment);

    @Mappings({ @Mapping(target = "invoice", ignore = true) })
    ShipmentEntity toEntity(Shipment shipment);
}
