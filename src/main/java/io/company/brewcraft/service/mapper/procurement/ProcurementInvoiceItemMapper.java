package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceItemDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.service.mapper.TaxMapper;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, MaterialMapper.class, TaxMapper.class })
public interface ProcurementInvoiceItemMapper extends BaseMapper<InvoiceItem, ProcurementInvoiceItemDto, AddProcurementInvoiceItemDto, UpdateProcurementInvoiceItemDto> {
    ProcurementInvoiceItemMapper INSTANCE = Mappers.getMapper(ProcurementInvoiceItemMapper.class);

    @Override
    ProcurementInvoiceItemDto toDto(InvoiceItem invoiceItem);

    @Override
    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_LAST_UPDATED, ignore = true)
    })
    InvoiceItem fromUpdateDto(UpdateProcurementInvoiceItemDto dto);

    @Override
    @Mappings({
        @Mapping(target = InvoiceItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = InvoiceItem.ATTR_ID, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_VERSION, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = InvoiceItem.ATTR_LAST_UPDATED, ignore = true)
    })
    InvoiceItem fromAddDto(AddProcurementInvoiceItemDto dto);
}