package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierContactDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.SupplierWithoutContactsDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.model.SupplierContactEntity;

@Mapper
public interface SupplierContactMapper {
    
    SupplierContactMapper INSTANCE = Mappers.getMapper(SupplierContactMapper.class);   
    
    SupplierContactWithSupplierDto supplierContactToSupplierContactWithSupplierDto(SupplierContactEntity contact);
    
    SupplierWithoutContactsDto supplierToSupplierWithoutContactsDto(SupplierEntity supplier);
    
    SupplierContactDto contactToContactDto(SupplierContactEntity contact);

    SupplierContactEntity contactDtoToContact(SupplierContactDto contactDto);
    
    SupplierContactEntity contactDtoToContact(AddSupplierContactDto contactDto);
    
    SupplierContactEntity updateContactDtoToContact(UpdateSupplierContactDto contactDto);

}
