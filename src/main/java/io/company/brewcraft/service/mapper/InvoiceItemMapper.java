package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.pojo.InvoiceItem;

@Mapper(uses = { QuantityMapper.class, MoneyMapper.class, MaterialMapper.class })
public interface InvoiceItemMapper {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    InvoiceItemDto toDto(InvoiceItem item);

    InvoiceItem fromDto(InvoiceItemDto dto);
    
    InvoiceItem fromDto(UpdateInvoiceItemDto dto);

    @Mappings({ @Mapping(target = "invoice", ignore = true) })
    InvoiceItemEntity toEntity(InvoiceItem item);

    InvoiceItem fromEntity(InvoiceItemEntity entity);
}
