package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.Invoice;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, InvoiceItemMapper.class, MaterialMapper.class, InvoiceStatusMapper.class, TaxMapper.class, ShipmentMapper.class, FreightMapper.class, PurchaseOrderMapper.class })
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    
    InvoiceDto toDto(Invoice invoice);
    
    Invoice fromDto(InvoiceDto dto);

    @Mappings({
        @Mapping(target = Invoice.ATTR_ID, ignore = true),
        @Mapping(target = Invoice.ATTR_PURCHASE_ORDER, source = "purchaseOrderId"),
        @Mapping(target = Invoice.ATTR_STATUS, source = "statusId"),
        @Mapping(target = Invoice.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Invoice.ATTR_CREATED_AT, ignore = true),
    })
    Invoice fromDto(UpdateInvoiceDto dto);

    @Mappings({
        @Mapping(target = Invoice.ATTR_ID, ignore = true),
        @Mapping(target = Invoice.ATTR_VERSION, ignore = true),
        @Mapping(target = Invoice.ATTR_PURCHASE_ORDER, source = "purchaseOrderId"),
        @Mapping(target = Invoice.ATTR_STATUS, source = "statusId"),
        @Mapping(target = Invoice.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Invoice.ATTR_CREATED_AT, ignore = true)
    })
    Invoice fromDto(AddInvoiceDto dto);
}
