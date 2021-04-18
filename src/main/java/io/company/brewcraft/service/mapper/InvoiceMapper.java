package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Invoice;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, InvoiceItemMapper.class, MaterialMapper.class, InvoiceStatusMapper.class, TaxMapper.class, ShipmentMapper.class, FreightMapper.class, PurchaseOrderMapper.class })
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    
    InvoiceDto toDto(Invoice invoice);
    
    Invoice fromDto(InvoiceDto dto);

    @Mappings({
        @Mapping(target = Identified.FIELD_ID, ignore = true),
        @Mapping(target = "purchaseOrder", source = "purchaseOrderId"),
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
    })
    Invoice fromDto(UpdateInvoiceDto dto);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true),
        @Mapping(target = "purchaseOrder", source = "purchaseOrderId"),
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "createdAt", ignore = true)
    })
    Invoice fromDto(AddInvoiceDto dto);
}
