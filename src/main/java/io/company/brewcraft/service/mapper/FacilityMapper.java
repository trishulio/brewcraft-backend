package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.Facility;

@Mapper(uses = { StorageMapper.class, EquipmentMapper.class, QuantityMapper.class, AddressMapper.class})
public interface FacilityMapper {
    
    FacilityMapper INSTANCE = Mappers.getMapper(FacilityMapper.class);
                    
    FacilityDto toDto(Facility facility);
    
    Facility fromDto(AddFacilityDto facilityDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Facility fromDto(UpdateFacilityDto facilityDto);
    
}
