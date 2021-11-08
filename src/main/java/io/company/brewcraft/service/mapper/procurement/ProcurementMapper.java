package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.service.mapper.FreightMapper;
import io.company.brewcraft.service.mapper.InvoiceStatusMapper;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.service.mapper.ShipmentMapper;
import io.company.brewcraft.service.mapper.ShipmentStatusMapper;
import io.company.brewcraft.service.mapper.StorageMapper;
import io.company.brewcraft.service.mapper.TaxMapper;

@Mapper(uses = { ProcurementIdMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, ProcurementItemMapper.class, MaterialMapper.class, InvoiceStatusMapper.class, TaxMapper.class, ShipmentMapper.class, FreightMapper.class, PurchaseOrderMapper.class, StorageMapper.class, ShipmentStatusMapper.class })
public interface ProcurementMapper extends BaseMapper<Procurement, ProcurementDto, AddProcurementDto, UpdateProcurementDto>{
    ProcurementMapper INSTANCE = Mappers.getMapper(ProcurementMapper.class);

    @Override
    ProcurementDto toDto(Procurement procurement);

    @Override
    @Mappings({
        @Mapping(target = Procurement.ATTR_ID, ignore = true),
        @Mapping(target = Procurement.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Procurement.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Procurement.ATTR_VERSION, ignore = true),
        @Mapping(target = Procurement.ATTR_LOTS, ignore = true),
        @Mapping(target = Procurement.ATTR_SHIPMENT_STATUS, source = "shipmentStatusId"),
        @Mapping(target = Procurement.ATTR_INVOICE_VERSION, ignore = true),
        @Mapping(target = Procurement.ATTR_INVOICE_ITEMS, ignore = true),
        @Mapping(target = Procurement.ATTR_INVOICE_STATUS, source = "invoiceStatusId")
    })
    Procurement fromAddDto(AddProcurementDto dto);

    @Override
    @Mappings({
        @Mapping(target = Procurement.ATTR_ID, ignore = true),
        @Mapping(target = Procurement.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = Procurement.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = Procurement.ATTR_LOTS, ignore = true),
        @Mapping(target = Procurement.ATTR_SHIPMENT_STATUS, source = "shipmentStatusId"),
        @Mapping(target = Procurement.ATTR_INVOICE_ITEMS, ignore = true),
        @Mapping(target = Procurement.ATTR_INVOICE_STATUS, source = "invoiceStatusId")
    })
    Procurement fromUpdateDto(UpdateProcurementDto dto);
}
