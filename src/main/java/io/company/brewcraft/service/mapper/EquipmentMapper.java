package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;

@Mapper(uses = { QuantityMapper.class})
public interface EquipmentMapper {
    
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    EquipmentDto equipmentToEquipmentDto(Equipment equipment);
    
    FacilityBaseDto facilityToFacilityDto(Facility facility);

    Equipment equipmentDtoToEquipment(EquipmentDto equipmentDto);
    
    Equipment equipmentDtoToEquipment(AddEquipmentDto equipmentDto);
    
    Equipment equipmentDtoToEquipment(UpdateEquipmentDto equipmentDto);
        
}
