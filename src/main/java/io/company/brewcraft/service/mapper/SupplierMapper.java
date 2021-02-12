package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.SupplierAddressEntity;
import io.company.brewcraft.model.SupplierContactEntity;
import io.company.brewcraft.model.SupplierEntity;

@Mapper
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDto supplierToSupplierDto(SupplierEntity supplier);

    SupplierEntity supplierDtoToSupplier(SupplierDto supplierDto);
    
    SupplierEntity supplierDtoToSupplier(AddSupplierDto supplierDto);
    
    SupplierEntity updateSupplierDtoToSupplier(UpdateSupplierDto supplierDto);
    
    SupplierContactDto contactToContactDto(SupplierContactEntity contact);

    SupplierContactEntity contactDtoToContact(SupplierContactDto contactDto);
    
    SupplierContactEntity updateContactDtoToContact(UpdateSupplierContactDto contactDto);
    
    AddressDto addressToAddressDto(SupplierAddressEntity address);

    SupplierAddressEntity addressDtoToAddress(AddressDto addressDto);
    
}
