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
public interface InvoiceItemMapper extends BaseMapper<InvoiceItem, InvoiceItemDto, AddInvoiceItemDto, UpdateInvoiceItemDto> {
    InvoiceItemMapper INSTANCE = Mappers.getMapper(InvoiceItemMapper.class);

    @Override
    InvoiceItemDto toDto(InvoiceItem invoiceItem);

    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_ID),
        @Mapping(target = InvoiceItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_VERSION, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_DESCRIPTION, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_PRICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_QUANTITY, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_TAX, ignore = true)
    })
    InvoiceItem fromDto(Long id);

    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
    })
    InvoiceItem fromDto(InvoiceItemDto dto);

    @Override
    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = InvoiceItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_LAST_UPDATED, ignore = true)
    })
    InvoiceItem fromUpdateDto(UpdateInvoiceItemDto dto);

    @Override
    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = InvoiceItem.ATTR_ID, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_VERSION, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_LAST_UPDATED, ignore = true)
    })
    InvoiceItem fromAddDto(AddInvoiceItemDto dto);
}
