package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
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

@Mapper(uses = { QuantityMapper.class, AddressMapper.class})
public interface EquipmentMapper {
    
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    EquipmentDto equipmentToEquipmentDto(EquipmentEntity equipment);
                
    FacilityEquipmentDto equipmentToFacilityEquipmentDto(EquipmentEntity equipment);

    EquipmentEntity facilityEquipmentDtoToEquipment(FacilityEquipmentDto equipmentDto);
    
    FacilityBaseDto facilityToFacilityBaseDto(FacilityEntity facility);
    
    @InheritInverseConfiguration
    Equipment fromEntity(EquipmentEntity equipment, @Context CycleAvoidingMappingContext context);
    
    EquipmentEntity toEntity(Equipment equipment, @Context CycleAvoidingMappingContext context);
    
    EquipmentDto toDto(Equipment equipment);
    
    Equipment fromDto(AddEquipmentDto equipment);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Equipment fromDto(UpdateEquipmentDto equipment);

}
