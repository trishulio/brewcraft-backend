package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.AddressDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.SupplierAddressEntity;
import io.company.brewcraft.model.SupplierEntity;

@Mapper(uses = { SupplierContactMapper.class})
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDto supplierToSupplierDto(SupplierEntity supplier);

    SupplierEntity supplierDtoToSupplier(SupplierDto supplierDto);
    
    SupplierEntity supplierDtoToSupplier(AddSupplierDto supplierDto);
    
    SupplierEntity updateSupplierDtoToSupplier(UpdateSupplierDto supplierDto);
    
    AddressDto addressToAddressDto(SupplierAddressEntity address);

    SupplierAddressEntity addressDtoToAddress(AddressDto addressDto);
    
}
