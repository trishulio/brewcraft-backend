package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.SupplierAddressDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.SupplierAddress;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.model.Supplier;

@Mapper
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDto supplierToSupplierDto(Supplier supplier);

    Supplier supplierDtoToSupplier(SupplierDto supplierDto);
    
    Supplier supplierDtoToSupplier(AddSupplierDto supplierDto);
    
    Supplier updateSupplierDtoToSupplier(UpdateSupplierDto supplierDto);
    
    SupplierContactDto contactToContactDto(SupplierContact contact);

    SupplierContact contactDtoToContact(SupplierContactDto contactDto);
    
    SupplierContact updateContactDtoToContact(UpdateSupplierContactDto contactDto);
    
    SupplierAddressDto addressToAddressDto(SupplierAddress address);

    SupplierAddress addressDtoToAddress(SupplierAddressDto addressDto);
    
}
