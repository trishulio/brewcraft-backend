package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.StorageEntity;
import io.company.brewcraft.pojo.Storage;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class} )
public interface StorageMapper {
    
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    StorageDto storageToStorageDto(StorageEntity storage);
                            
    FacilityBaseDto facilityToFacilityBaseDto(FacilityEntity facility);
    
    @InheritInverseConfiguration
    Storage fromEntity(StorageEntity storage, @Context CycleAvoidingMappingContext context);

    StorageEntity toEntity(Storage storage, @Context CycleAvoidingMappingContext context);
    
    StorageDto toDto(Storage storage);
    
    Storage fromDto(AddStorageDto storage);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Storage fromDto(UpdateStorageDto storage);
    
}
