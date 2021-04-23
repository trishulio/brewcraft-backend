package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.Versioned;

@Mapper(uses = { SupplierContactMapper.class, AddressMapper.class})
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);
    
    SupplierDto toDto(Supplier supplier);
    
    @Mappings({
        @Mapping(target = Supplier.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Supplier.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Supplier.ATTR_VERSION, ignore = true),
    })
    Supplier fromDto(AddSupplierDto supplierDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Supplier fromDto(UpdateSupplierDto supplierDto);
    
}
