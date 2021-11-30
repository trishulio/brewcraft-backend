package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementPurchaseOrderDto;
import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementPurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.service.mapper.SupplierMapper;

@Mapper(uses = { SupplierMapper.class })
public interface ProcurementPurchaseOrderMapper extends BaseMapper<PurchaseOrder, ProcurementPurchaseOrderDto, AddProcurementPurchaseOrderDto, UpdateProcurementPurchaseOrderDto>{
    final ProcurementPurchaseOrderMapper INSTANCE = Mappers.getMapper(ProcurementPurchaseOrderMapper.class);

    @Override
    ProcurementPurchaseOrderDto toDto(PurchaseOrder e);

    @Override
    @Mapping(target = PurchaseOrder.ATTR_ID, ignore = true)
    @Mapping(target = PurchaseOrder.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = PurchaseOrder.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = PurchaseOrder.ATTR_VERSION, ignore = true)
    @Mapping(target = PurchaseOrder.ATTR_SUPPLIER, source = "supplierId")
    PurchaseOrder fromAddDto(AddProcurementPurchaseOrderDto dto);

    @Override
    @Mapping(target = PurchaseOrder.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = PurchaseOrder.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = PurchaseOrder.ATTR_SUPPLIER, source = "supplierId")
    PurchaseOrder fromUpdateDto(UpdateProcurementPurchaseOrderDto dto);
}
