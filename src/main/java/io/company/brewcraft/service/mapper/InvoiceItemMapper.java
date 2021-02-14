package io.company.brewcraft.service.mapper;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.pojo.InvoiceItem;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, MaterialMapper.class, TaxMapper.class })
public interface InvoiceItemMapper {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    InvoiceItemDto toDto(InvoiceItem item);

    InvoiceItem fromDto(InvoiceItemDto dto);
    
    InvoiceItem fromDto(UpdateInvoiceItemDto dto);

    @Mappings({ @Mapping(target = "invoice", ignore = true) })
    InvoiceItemEntity toEntity(InvoiceItem item, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    InvoiceItem fromEntity(InvoiceItemEntity entity, @Context CycleAvoidingMappingContext context);
}
