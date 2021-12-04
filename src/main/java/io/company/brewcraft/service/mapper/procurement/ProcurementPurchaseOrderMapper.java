package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.mapper.SupplierMapper;

@Mapper(uses = { SupplierMapper.class })
public interface ProcurementPurchaseOrderMapper {
    final ProcurementPurchaseOrderMapper INSTANCE = Mappers.getMapper(ProcurementPurchaseOrderMapper.class);

    ProcurementPurchaseOrderDto toDto(PurchaseOrder purchaseOrder);
}
