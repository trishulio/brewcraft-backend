package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddInvoiceItemDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.InvoiceItem;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, MaterialMapper.class, TaxMapper.class })
public interface InvoiceItemMapper {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    InvoiceItemDto toDto(InvoiceItem item);

    InvoiceItem fromDto(InvoiceItemDto dto);

    @Mappings({
        @Mapping(source = "materialId", target = "material")
    })
    InvoiceItem fromDto(UpdateInvoiceItemDto dto);

    @Mappings({
        @Mapping(source = "materialId", target = "material"),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    InvoiceItem fromDto(AddInvoiceItemDto dto);
}
