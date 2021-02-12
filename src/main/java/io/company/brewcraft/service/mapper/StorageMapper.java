package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.FacilityAddressEntity;
import io.company.brewcraft.model.StorageEntity;
import io.company.brewcraft.pojo.Storage;

@Mapper(uses = { FacilityMapper.class})
public interface StorageMapper {
    
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    StorageDto storageToStorageDto(StorageEntity storage);
            
    AddressDto addressToAddressDto(FacilityAddressEntity facilityAddress);

    StorageEntity storageDtoToStorage(FacilityStorageDto storageDto);
    
    StorageEntity storageDtoToStorage(AddStorageDto storageDto);

    StorageEntity storageDtoToStorage(UpdateStorageDto storageDto);
    
    StorageEntity storagePojoToStorage(Storage storage);

    Storage storageEntityToStorage(StorageEntity storageEntity);
    
    FacilityStorageDto storageToFacilityStorageDto(StorageEntity storage);

    StorageEntity facilityStorageDtoToStorage(FacilityStorageDto storageDto);
    
}
