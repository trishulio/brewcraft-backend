package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.FacilityAddress;
import io.company.brewcraft.model.Storage;

@Mapper(uses = { QuantityMapper.class})
public interface FacilityMapper {
    
    FacilityMapper INSTANCE = Mappers.getMapper(FacilityMapper.class);

    FacilityDto facilityToFacilityDto(Facility facility);

    Facility facilityDtoToFacility(FacilityDto facilityDto);
    
    Facility facilityDtoToFacility(AddFacilityDto facilityDto);

    Facility facilityDtoToFacility(UpdateFacilityDto facilityDto);
        
    AddressDto addressToAddressDto(FacilityAddress address);

    FacilityAddress addressDtoToAddress(AddressDto addressDto);
    
    FacilityEquipmentDto equipmentToEquipmentDto(Equipment equipment);

    Equipment equipmentDtoToEquipment(FacilityEquipmentDto equipmentDto);
    
    FacilityStorageDto storageToStorageDto(Storage storage);

    Storage storageDtoToStorage(FacilityStorageDto storageDto);
    
}
