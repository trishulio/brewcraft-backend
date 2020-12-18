package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Storage;

@Mapper
public interface StorageMapper {
    
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    FacilityStorageDto storageToStorageDto(Storage storage);
    
    FacilityBaseDto facilityToFacilityDto(Facility facility);

    Storage storageDtoToStorage(FacilityStorageDto storageDto);
    
    Storage storageDtoToStorage(AddStorageDto storageDto);

    Storage storageDtoToStorage(UpdateStorageDto storageDto);
       
}
