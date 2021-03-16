package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.pojo.ShipmentStatus;

@Mapper
public interface ShipmentStatusMapper {
    ShipmentStatusMapper INSTANCE = Mappers.getMapper(ShipmentStatusMapper.class);

    ShipmentStatusDto toDto(ShipmentStatus status);

    ShipmentStatus fromDto(ShipmentStatusDto status);
}
