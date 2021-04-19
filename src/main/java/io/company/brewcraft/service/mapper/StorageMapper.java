package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Storage;

@Mapper(uses = { QuantityMapper.class } )
public interface StorageMapper {
    
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);
                            
    FacilityBaseDto facilityToFacilityBaseDto(Facility facility);
    
    FacilityStorageDto toFacilityStorageDto(Storage storage);
    
    StorageDto toDto(Storage storage);
    
    default Storage fromDto(Long id) {
        Storage storage = null;
        if (id != null) {
            storage = new Storage(id);
        }
        return storage;
    }

    Storage fromDto(AddStorageDto storage);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Storage fromDto(UpdateStorageDto storage);
    
}
