package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.pojo.Equipment;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, AddressMapper.class})
public interface EquipmentMapper {
    
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);
    
    FacilityBaseDto facilityToFacilityBaseDto(FacilityEntity facility);
    
    @InheritInverseConfiguration
    Equipment fromEntity(EquipmentEntity equipment, @Context CycleAvoidingMappingContext context);
    
    EquipmentEntity toEntity(Equipment equipment, @Context CycleAvoidingMappingContext context);
    
    @Mapping(target = "maxCapacity", source = "maxCapacityInDisplayUnit")
    EquipmentDto toDto(Equipment equipment);
    
    @Mapping(target = "displayUnit", source = "maxCapacity.symbol")
    Equipment fromDto(AddEquipmentDto equipment);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "displayUnit", source = "maxCapacity.symbol")
    Equipment fromDto(UpdateEquipmentDto equipment);
    
    @Mapping(target = "displayUnit", source = "maxCapacity.symbol")
    Equipment fromDto(FacilityEquipmentDto equipment);
}
