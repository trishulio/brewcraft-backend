package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Invoice;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, AddressMapper.class, EquipmentTypeMapper.class, FacilityMapper.class})
public interface EquipmentMapper extends BaseMapper<Equipment, EquipmentDto, AddEquipmentDto, UpdateEquipmentDto>  {
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    Equipment fromDto(Long id);

    @Override
    EquipmentDto toDto(Equipment equipment);

    @Override
    @Mapping(target = Equipment.ATTR_ID, ignore = true)
    @Mapping(target = Equipment.ATTR_VERSION, ignore = true)
    @Mapping(target = Equipment.FIELD_TYPE, source = "typeId")
    @Mapping(target = Equipment.ATTR_FACILITY, source = "facilityId")
    @Mapping(target = Equipment.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Equipment.ATTR_CREATED_AT, ignore = true)
    Equipment fromAddDto(AddEquipmentDto equipment);

    @Override
    @Mapping(target = Equipment.FIELD_TYPE, source = "typeId")
    @Mapping(target = Equipment.ATTR_FACILITY, source = "facilityId")
    @Mapping(target = Equipment.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Equipment.ATTR_CREATED_AT, ignore = true)
    Equipment fromUpdateDto(UpdateEquipmentDto equipment);
}
