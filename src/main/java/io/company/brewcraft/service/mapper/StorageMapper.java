package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.FacilityAddressEntity;
import io.company.brewcraft.model.StorageEntity;

@Mapper
public interface StorageMapper {
    
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    StorageDto storageToStorageDto(StorageEntity storage);
        
    FacilityBaseDto facilityToFacilityDto(FacilityEntity facility);
    
    AddressDto addressToAddressDto(FacilityAddressEntity facilityAddress);

    StorageEntity storageDtoToStorage(FacilityStorageDto storageDto);
    
    StorageEntity storageDtoToStorage(AddStorageDto storageDto);

    StorageEntity storageDtoToStorage(UpdateStorageDto storageDto);
       
}
