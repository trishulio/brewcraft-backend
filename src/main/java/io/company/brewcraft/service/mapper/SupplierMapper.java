package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.Supplier;

@Mapper(uses = { SupplierContactMapper.class, AddressMapper.class})
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);
    
    SupplierDto toDto(Supplier supplier);
    
    Supplier fromDto(AddSupplierDto supplierDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Supplier fromDto(UpdateSupplierDto supplierDto);
    
}
