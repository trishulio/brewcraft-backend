package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;
import io.company.brewcraft.service.mapper.ShipmentMapper;

@Mapper(uses = { InvoiceMapper.class, ShipmentMapper.class, PurchaseOrderMapper.class })
public interface ProcurementMapper {
    ProcurementMapper INSTANCE = Mappers.getMapper(ProcurementMapper.class);

    @Mappings({
        @Mapping(target = "shipment", ignore = true)
    })
    Procurement fromDto(AddProcurementDto addProcurement);

    ProcurementDto toDto(Procurement procurement);
}
