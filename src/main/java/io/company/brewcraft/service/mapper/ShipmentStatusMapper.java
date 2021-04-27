package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.model.ShipmentStatus;

@Mapper
public interface ShipmentStatusMapper {
    ShipmentStatusMapper INSTANCE = Mappers.getMapper(ShipmentStatusMapper.class);

    @Mappings({
        @Mapping(target = ShipmentStatus.ATTR_ID)
    })
    ShipmentStatus fromDto(String id);

    @Mappings({
        @Mapping(target = "name", source = ShipmentStatus.ATTR_ID)
    })
    ShipmentStatusDto toDto(ShipmentStatus status);

    @Mappings({
        @Mapping(target = ShipmentStatus.ATTR_ID, source = "name"),
        @Mapping(target = ShipmentStatus.ATTR_VERSION, ignore = true),
        @Mapping(target = ShipmentStatus.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = ShipmentStatus.ATTR_CREATED_AT, ignore = true)
    })
    ShipmentStatus fromDto(ShipmentStatusDto status);
}
