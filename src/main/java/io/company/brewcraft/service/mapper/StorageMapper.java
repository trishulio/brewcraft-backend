package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.FacilityAddress;
import io.company.brewcraft.model.Storage;

@Mapper
public interface StorageMapper {
    
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    StorageDto storageToStorageDto(Storage storage);
        
    FacilityBaseDto facilityToFacilityDto(Facility facility);
    
    AddressDto addressToAddressDto(FacilityAddress facilityAddress);

    Storage storageDtoToStorage(FacilityStorageDto storageDto);
    
    Storage storageDtoToStorage(AddStorageDto storageDto);

    Storage storageDtoToStorage(UpdateStorageDto storageDto);
       
}
