package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.EquipmentTypeDto;
import io.company.brewcraft.model.EquipmentType;

@Mapper
public interface EquipmentTypeMapper {
    EquipmentTypeMapper INSTANCE = Mappers.getMapper(EquipmentTypeMapper.class);

    @Mapping(target = EquipmentType.ATTR_ID)
    @Mapping(target = EquipmentType.ATTR_VERSION, ignore = true)
    @Mapping(target = EquipmentType.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = EquipmentType.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = EquipmentType.ATTR_NAME, ignore = true)
    EquipmentType fromDto(Long id);

    EquipmentTypeDto toDto(EquipmentType equipmentType);
}
