package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.FacilityAddressEntity;

@Mapper(uses = { StorageMapper.class, EquipmentMapper.class, QuantityMapper.class})
public interface FacilityMapper {
    
    FacilityMapper INSTANCE = Mappers.getMapper(FacilityMapper.class);

    FacilityDto facilityToFacilityDto(FacilityEntity facility);

    FacilityEntity facilityDtoToFacility(FacilityDto facilityDto);
    
    FacilityEntity facilityDtoToFacility(AddFacilityDto facilityDto);

    FacilityEntity facilityDtoToFacility(UpdateFacilityDto facilityDto);
        
    AddressDto addressToAddressDto(FacilityAddressEntity address);
    
    EquipmentEntity equipmentDtoToEquipment(AddEquipmentDto equipmentDto);
    
    FacilityBaseDto facilityToFacilityBaseDto(FacilityEntity facility);
    
}
