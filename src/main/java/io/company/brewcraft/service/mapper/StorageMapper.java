package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = Storage.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Storage.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Storage.ATTR_VERSION, ignore = true)
    Storage fromDto(Long id);

    @Mapping(target = Storage.ATTR_ID, ignore = true)
    @Mapping(target = Storage.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Storage.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Storage.ATTR_VERSION, ignore = true)
    Storage fromDto(AddStorageDto storage);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = Storage.ATTR_ID, ignore = true)
    @Mapping(target = Storage.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Storage.ATTR_CREATED_AT, ignore = true)
    Storage fromDto(UpdateStorageDto storage);
}
