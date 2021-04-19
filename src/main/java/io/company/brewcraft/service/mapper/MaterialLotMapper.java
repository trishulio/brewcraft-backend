package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialLotDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.model.MaterialLot;

@Mapper(uses = {QuantityMapper.class, MaterialMapper.class, InvoiceItemMapper.class, StorageMapper.class})
public interface MaterialLotMapper {

    MaterialLotMapper INSTANCE = Mappers.getMapper(MaterialLotMapper.class);

    MaterialLotDto toDto(MaterialLot lot);
    
    @Mappings({
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "shipment", ignore = true),
        @Mapping(target = "material", source = "materialId"),
        @Mapping(target = "invoiceItem", source = "invoiceItemId"),
        @Mapping(target = "storage", source = "storageId")
    })
    MaterialLot fromDto(UpdateMaterialLotDto item);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "lastUpdated", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "shipment", ignore = true),
        @Mapping(target = "material", source = "materialId"),
        @Mapping(target = "invoiceItem", source = "invoiceItemId"),
        @Mapping(target = "storage", source = "storageId"),
        @Mapping(target = "version", ignore = true)
    })
    MaterialLot fromDto(AddMaterialLotDto item);
}
