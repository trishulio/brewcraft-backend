package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, AddressMapper.class})
public interface EquipmentMapper {
    
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);
    
    FacilityBaseDto facilityToFacilityBaseDto(Facility facility);

    @Mapping(target = "maxCapacity", source = "maxCapacityInDisplayUnit")
    EquipmentDto toDto(Equipment equipment);
    
    @Mapping(target = "maxCapacity", source = "maxCapacityInDisplayUnit")
    FacilityEquipmentDto toFacilityEquipmentDto(Equipment equipment);
    
    @Mapping(target = "maxCapacityDisplayUnit", source = "maxCapacity.symbol")
    Equipment fromDto(AddEquipmentDto equipment);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "maxCapacityDisplayUnit", source = "maxCapacity.symbol")
    Equipment fromDto(UpdateEquipmentDto equipment);
    
    @Mapping(target = "maxCapacityDisplayUnit", source = "maxCapacity.symbol")
    Equipment fromDto(FacilityEquipmentDto equipment);
}
