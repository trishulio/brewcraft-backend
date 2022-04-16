package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.Invoice;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, AmountMapper.class, InvoiceItemMapper.class, MaterialMapper.class, InvoiceStatusMapper.class, TaxMapper.class, ShipmentMapper.class, FreightMapper.class, PurchaseOrderMapper.class })
public interface InvoiceMapper extends BaseMapper<Invoice, InvoiceDto, AddInvoiceDto, UpdateInvoiceDto> {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Override
    InvoiceDto toDto(Invoice invoice);

    @Override
    @Mapping(target = Invoice.ATTR_INVOICE_NUMBER, source = "invoiceNumber")
    @Mapping(target = Invoice.ATTR_PURCHASE_ORDER, source = "purchaseOrderId")
    @Mapping(target = Invoice.ATTR_INVOICE_STATUS, source = "invoiceStatusId")
    @Mapping(target = Invoice.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Invoice.ATTR_CREATED_AT, ignore = true)
    Invoice fromUpdateDto(UpdateInvoiceDto dto);

    @Override
    @Mapping(target = Invoice.ATTR_ID, ignore = true)
    @Mapping(target = Invoice.ATTR_VERSION, ignore = true)
    @Mapping(target = Invoice.ATTR_INVOICE_NUMBER, source = "invoiceNumber")
    @Mapping(target = Invoice.ATTR_PURCHASE_ORDER, source = "purchaseOrderId")
    @Mapping(target = Invoice.ATTR_INVOICE_STATUS, source = "invoiceStatusId")
    @Mapping(target = Invoice.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Invoice.ATTR_CREATED_AT, ignore = true)
    Invoice fromAddDto(AddInvoiceDto dto);
}
