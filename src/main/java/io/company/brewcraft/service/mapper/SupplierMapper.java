package io.company.brewcraft.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.SupplierAddressDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.model.SupplierAddress;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.model.Supplier;

@Mapper
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDto supplierToSupplierDto(Supplier supplier);

    Supplier supplierDtoToSupplier(SupplierDto supplierDto);
    
    SupplierContactDto contactToContactDto(SupplierContact contact);

    SupplierContact contactDtoToContact(SupplierContactDto contactDto);
    
    SupplierAddressDto addressToAddressDto(SupplierAddress address);

    SupplierAddress addressDtoToAddress(SupplierAddressDto addressDto);
    
    @AfterMapping
    default void afterSupplierDtoToSupplier(SupplierDto supplierDto, @MappingTarget Supplier supplier) {
        if (supplier.getContacts() != null) {
            supplier.getContacts().forEach(contact -> contact.setSupplier(supplier));
        }
    }
}
