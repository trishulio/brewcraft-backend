package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.FacilityBaseDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.pojo.Facility;

@Mapper(uses = { StorageMapper.class, EquipmentMapper.class, QuantityMapper.class, AddressMapper.class})
public interface FacilityMapper {
    
    FacilityMapper INSTANCE = Mappers.getMapper(FacilityMapper.class);

    FacilityDto facilityToFacilityDto(FacilityEntity facility);
                
    FacilityBaseDto facilityToFacilityBaseDto(FacilityEntity facility);
    
    @InheritInverseConfiguration
    Facility fromEntity(FacilityEntity facility, @Context CycleAvoidingMappingContext context);
    
    FacilityEntity toEntity(Facility facility, @Context CycleAvoidingMappingContext context);
    
    FacilityDto toDto(Facility facility);
    
    Facility fromDto(AddFacilityDto facilityDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Facility fromDto(UpdateFacilityDto facilityDto);
    
}
