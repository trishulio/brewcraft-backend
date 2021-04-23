package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialLotDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Versioned;

@Mapper(uses = {QuantityMapper.class, MaterialMapper.class, InvoiceItemMapper.class, StorageMapper.class})
public interface MaterialLotMapper {

    MaterialLotMapper INSTANCE = Mappers.getMapper(MaterialLotMapper.class);

    MaterialLotDto toDto(MaterialLot lot);
    
    @Mappings({
        @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true),
        @Mapping(target = MaterialLot.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, source = "invoiceItemId"),
        @Mapping(target = MaterialLot.ATTR_STORAGE, source = "storageId")
    })
    MaterialLot fromDto(UpdateMaterialLotDto item);

    @Mappings({
        @Mapping(target = MaterialLot.ATTR_ID, ignore = true),
        @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true),
        @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true),
        @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true),
        @Mapping(target = MaterialLot.ATTR_MATERIAL, source = "materialId"),
        @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, source = "invoiceItemId"),
        @Mapping(target = MaterialLot.ATTR_STORAGE, source = "storageId"),
        @Mapping(target = MaterialLot.ATTR_VERSION, ignore = true)
    })
    MaterialLot fromDto(AddMaterialLotDto item);
}
