package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierContactDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.SupplierWithoutContactsDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.pojo.SupplierContact;
import io.company.brewcraft.model.SupplierContactEntity;

@Mapper
public interface SupplierContactMapper {
    
    SupplierContactMapper INSTANCE = Mappers.getMapper(SupplierContactMapper.class);   
    
    SupplierContactWithSupplierDto supplierContactToSupplierContactWithSupplierDto(SupplierContactEntity contact);
    
    SupplierWithoutContactsDto supplierToSupplierWithoutContactsDto(SupplierEntity supplier);
    
    SupplierContactDto contactToContactDto(SupplierContactEntity contact);
            
    @InheritInverseConfiguration
    SupplierContact fromEntity(SupplierContactEntity contact, @Context CycleAvoidingMappingContext context);

    SupplierContactEntity toEntity(SupplierContact contact, @Context CycleAvoidingMappingContext context);
      
    SupplierContactDto toDto(SupplierContact contact);
    
    SupplierContactWithSupplierDto toDtoWithSupplier(SupplierContact contact);
    
    SupplierContact fromDto(AddSupplierContactDto contactDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    SupplierContact fromDto(UpdateSupplierContactDto contactDto);
        
}
