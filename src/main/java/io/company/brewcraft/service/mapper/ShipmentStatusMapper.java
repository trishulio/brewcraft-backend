package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.model.ShipmentStatus;

@Mapper
public interface ShipmentStatusMapper {
    ShipmentStatusMapper INSTANCE = Mappers.getMapper(ShipmentStatusMapper.class);

    default ShipmentStatus fromDto(String statusName) {
        ShipmentStatus status = null;
        if (statusName != null) {
            status = new ShipmentStatus(statusName);
        }
        
        return status;
    }

    ShipmentStatusDto toDto(ShipmentStatus status);

    ShipmentStatus fromDto(ShipmentStatusDto status);
}
