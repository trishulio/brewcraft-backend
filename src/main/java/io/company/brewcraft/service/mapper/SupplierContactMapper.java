package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.SupplierWithoutContactsDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;

@Mapper
public interface SupplierContactMapper {
    
    SupplierContactMapper INSTANCE = Mappers.getMapper(SupplierContactMapper.class);   
    
    SupplierContactWithSupplierDto supplierContactToSupplierContactWithSupplierDto(SupplierContact contact);
    
    SupplierWithoutContactsDto supplierToSupplierWithoutContactsDto(Supplier supplier);

}
