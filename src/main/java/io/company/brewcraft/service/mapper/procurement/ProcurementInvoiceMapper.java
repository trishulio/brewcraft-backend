package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceDto;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.service.mapper.FreightMapper;
import io.company.brewcraft.service.mapper.InvoiceStatusMapper;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.service.mapper.ShipmentMapper;
import io.company.brewcraft.service.mapper.TaxMapper;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, ProcurementInvoiceItemMapper.class, MaterialMapper.class, InvoiceStatusMapper.class, TaxMapper.class, ShipmentMapper.class, FreightMapper.class, PurchaseOrderMapper.class })
public interface ProcurementInvoiceMapper extends BaseMapper<Invoice, ProcurementInvoiceDto, AddProcurementInvoiceDto, UpdateProcurementInvoiceDto> {
    ProcurementInvoiceMapper INSTANCE = Mappers.getMapper(ProcurementInvoiceMapper.class);

    @Override
    ProcurementInvoiceDto toDto(Invoice invoice);

    @Override
    @Mappings({
        @Mapping(target = Invoice.ATTR_INVOICE_STATUS, source = "invoiceStatusId"),
        @Mapping(target = Invoice.ATTR_PURCHASE_ORDER, ignore = true),
        @Mapping(target = Invoice.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Invoice.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Invoice.ATTR_INVOICE_ITEMS, ignore = true),
    })    Invoice fromUpdateDto(UpdateProcurementInvoiceDto dto);

    @Override
    @Mappings({
        @Mapping(target = Invoice.ATTR_INVOICE_STATUS, source = "invoiceStatusId"),
        @Mapping(target = Invoice.ATTR_ID, ignore = true),
        @Mapping(target = Invoice.ATTR_VERSION, ignore = true),
        @Mapping(target = Invoice.ATTR_PURCHASE_ORDER, ignore = true),
        @Mapping(target = Invoice.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Invoice.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Invoice.ATTR_INVOICE_ITEMS, ignore = true),
    })
    Invoice fromAddDto(AddProcurementInvoiceDto dto);
}
