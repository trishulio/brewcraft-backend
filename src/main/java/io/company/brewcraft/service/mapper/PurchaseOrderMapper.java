package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;

@Mapper(uses = { SupplierMapper.class })
public interface PurchaseOrderMapper {
    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    @Mappings({
        @Mapping(target = PurchaseOrder.ATTR_ID),
        @Mapping(target = PurchaseOrder.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_VERSION, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_ORDER_NUMBER, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_SUPPLIER, ignore = true)
    })
    PurchaseOrder fromDto(Long id);

    @Mappings({
        @Mapping(target = PurchaseOrder.ATTR_ID, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_VERSION, ignore = true),
        @Mapping(target = PurchaseOrder.ATTR_SUPPLIER, source = "supplierId")
    })
    PurchaseOrder fromDto(AddPurchaseOrderDto dto);

    PurchaseOrderDto toDto(PurchaseOrder po);
}
