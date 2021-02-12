package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.FacilityAddressEntity;
import io.company.brewcraft.model.StorageEntity;

@Mapper(uses = { QuantityMapper.class})
public interface FacilityMapper {
    
    FacilityMapper INSTANCE = Mappers.getMapper(FacilityMapper.class);

    FacilityDto facilityToFacilityDto(FacilityEntity facility);

    FacilityEntity facilityDtoToFacility(FacilityDto facilityDto);
    
    FacilityEntity facilityDtoToFacility(AddFacilityDto facilityDto);

    FacilityEntity facilityDtoToFacility(UpdateFacilityDto facilityDto);
        
    AddressDto addressToAddressDto(FacilityAddressEntity address);

    FacilityAddressEntity addressDtoToAddress(AddressDto addressDto);
    
    FacilityEquipmentDto equipmentToEquipmentDto(EquipmentEntity equipment);

    EquipmentEntity equipmentDtoToEquipment(FacilityEquipmentDto equipmentDto);
    
    EquipmentEntity equipmentDtoToEquipment(AddEquipmentDto equipmentDto);
    
    FacilityStorageDto storageToStorageDto(StorageEntity storage);

    StorageEntity storageDtoToStorage(FacilityStorageDto storageDto);
    
    StorageEntity storageDtoToStorage(AddStorageDto storageDto);
    
}
