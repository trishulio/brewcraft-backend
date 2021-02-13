package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
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
    
    @Mappings({@Mapping(target = "equipment", ignore = true),
        @Mapping(target = "storages", ignore = true)})
    Facility fromEntity(FacilityEntity facility);
    
    @Mappings({@Mapping(target = "equipment", ignore = true),
        @Mapping(target = "storages", ignore = true)})
    FacilityEntity toEntity(Facility facility);
    
    FacilityDto toDto(Facility facility);
    
    Facility fromDto(AddFacilityDto facilityDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Facility fromDto(UpdateFacilityDto facilityDto);
    
    @BeforeMapping
    default void beforeFromEntity(@MappingTarget Facility target, FacilityEntity entity) {
        EquipmentMapper equipmentMapper = EquipmentMapper.INSTANCE;
        StorageMapper storageMapper = StorageMapper.INSTANCE;
        
        if (entity.getEquipment() != null) {
            entity.getEquipment().forEach(eq -> {
                target.addEquipment(equipmentMapper.fromEntity(eq, new CycleAvoidingMappingContext()));
            });
        }
        
        if (entity.getStorages() != null) {
            entity.getStorages().forEach(storage -> {
                target.addStorage(storageMapper.fromEntity(storage, new CycleAvoidingMappingContext()));
            });
        }
    }

    @BeforeMapping
    default void beforeToEntity(@MappingTarget FacilityEntity target, Facility facility) {
        EquipmentMapper equipmentMapper = EquipmentMapper.INSTANCE;
        StorageMapper storageMapper = StorageMapper.INSTANCE;
        
        if (facility.getEquipment() != null) {
            facility.getEquipment().forEach(eq -> {
                target.addEquipment(equipmentMapper.toEntity(eq, new CycleAvoidingMappingContext()));
            });
        }
        
        if (facility.getStorages() != null) {
            facility.getStorages().forEach(eq -> {
                target.addStorage(storageMapper.toEntity(eq, new CycleAvoidingMappingContext()));
            });
        }
    }
    
}
