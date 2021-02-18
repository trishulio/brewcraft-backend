package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.pojo.Supplier;

@Mapper(uses = { SupplierContactMapper.class, AddressMapper.class})
public interface SupplierMapper {
    
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDto supplierToSupplierDto(SupplierEntity supplier);
            
    @Mappings({@Mapping(target = "contacts", ignore = true)})
    Supplier fromEntity(SupplierEntity supplier);

    @Mappings({@Mapping(target = "contacts", ignore = true)})
    SupplierEntity toEntity(Supplier supplier);
    
    SupplierDto toDto(Supplier supplier);
    
    Supplier fromDto(AddSupplierDto supplierDto);
    
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Supplier fromDto(UpdateSupplierDto supplierDto);
    
    @BeforeMapping
    default void beforeFromEntity(@MappingTarget Supplier target, SupplierEntity entity) {
        SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;
        
        if (entity.getContacts() != null) {
            entity.getContacts().forEach(contact -> {
                target.addContact(supplierContactMapper.fromEntity(contact, new CycleAvoidingMappingContext()));
            });
        }
    }

    @BeforeMapping
    default void beforeToEntity(@MappingTarget SupplierEntity target, Supplier supplier) {
        SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;
       
        if (supplier.getContacts() != null) {
            supplier.getContacts().forEach(contact -> {
                target.addContact(supplierContactMapper.toEntity(contact, new CycleAvoidingMappingContext()));
            });
        }
    }
}
