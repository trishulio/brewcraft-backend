package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.service.mapper.StorageMapper;
import io.company.brewcraft.service.mapper.TaxMapper;

@Mapper(uses = { ProcurementItemIdMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, MaterialMapper.class, TaxMapper.class, StorageMapper.class })
public interface ProcurementItemMapper {
    ProcurementItemMapper INSTANCE = Mappers.getMapper(ProcurementItemMapper.class);

    ProcurementItemDto toDto(ProcurementItem item);

    @Mappings({
        @Mapping(target = ProcurementItem.ATTR_ID, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_VERSION, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_INVOICE_ITEM_VERSION, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_PROCUREMENT, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_SHIPMENT, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_INVOICE_ITEM, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = ProcurementItem.ATTR_STORAGE, source = "storageId")
    })
    ProcurementItem fromDto(AddProcurementItemDto dto);

    @Mappings({
        @Mapping(target = ProcurementItem.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_INVOICE, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_PROCUREMENT, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_SHIPMENT, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_INVOICE_ITEM, ignore = true),
        @Mapping(target = ProcurementItem.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = ProcurementItem.ATTR_STORAGE, source = "storageId")
    })
    ProcurementItem fromDto(UpdateProcurementItemDto dto);
}
