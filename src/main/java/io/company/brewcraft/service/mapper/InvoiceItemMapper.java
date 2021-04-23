package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddInvoiceItemDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Versioned;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, MaterialMapper.class, TaxMapper.class })
public interface InvoiceItemMapper {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    InvoiceItemDto toDto(InvoiceItem item);

    default InvoiceItem fromDto(Long id) {
        InvoiceItem item = null;
        if (id != null) {
            item = new InvoiceItem(id);
        }
        
        return item;
    }

    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true)
    })
    InvoiceItem fromDto(InvoiceItemDto dto);

    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, source = "materialId")
    })
    InvoiceItem fromDto(UpdateInvoiceItemDto dto);

    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = InvoiceItem.ATTR_ID, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_VERSION, ignore = true)
    })
    InvoiceItem fromDto(AddInvoiceItemDto dto);
}
