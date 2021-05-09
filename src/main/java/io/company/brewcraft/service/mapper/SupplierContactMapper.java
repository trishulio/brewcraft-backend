package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierContactDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.SupplierWithoutContactsDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierContactWithSupplierDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;

@Mapper
public interface SupplierContactMapper {
    
    SupplierContactMapper INSTANCE = Mappers.getMapper(SupplierContactMapper.class);   
    
    SupplierContactWithSupplierDto supplierContactToSupplierContactWithSupplierDto(SupplierContact contact);
    
    SupplierWithoutContactsDto supplierToSupplierWithoutContactsDto(Supplier supplier);
          
    SupplierContactDto toDto(SupplierContact contact);
    
    SupplierContactWithSupplierDto toDtoWithSupplier(SupplierContact contact);
    
    @Mappings({
        @Mapping(target = SupplierContact.ATTR_ID, ignore = true),
        @Mapping(target = SupplierContact.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = SupplierContact.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = SupplierContact.ATTR_VERSION, ignore = true)
    })
    SupplierContact fromDto(AddSupplierContactDto contactDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mappings({
        @Mapping(target = SupplierContact.ATTR_ID, ignore = true),
        @Mapping(target = SupplierContact.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = SupplierContact.ATTR_CREATED_AT, ignore = true),
    })
    SupplierContact fromDto(UpdateSupplierContactDto contactDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mappings({
        @Mapping(target = SupplierContact.ATTR_ID, ignore = true),
        @Mapping(target = SupplierContact.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = SupplierContact.ATTR_CREATED_AT, ignore = true),
    })
    SupplierContact fromDto(UpdateSupplierContactWithSupplierDto contactDto);        
}
