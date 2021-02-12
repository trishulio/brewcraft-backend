package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.FacilityAddressEntity;

@Mapper(uses = { QuantityMapper.class})
public interface EquipmentMapper {
    
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    EquipmentDto equipmentToEquipmentDto(EquipmentEntity equipment);
    
    FacilityBaseDto facilityToFacilityDto(FacilityEntity facility);
    
    AddressDto addressToAddressDto(FacilityAddressEntity facilityAddress);

    EquipmentEntity equipmentDtoToEquipment(EquipmentDto equipmentDto);
    
    EquipmentEntity equipmentDtoToEquipment(AddEquipmentDto equipmentDto);
    
    EquipmentEntity equipmentDtoToEquipment(UpdateEquipmentDto equipmentDto);
        
}
