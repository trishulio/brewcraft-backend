package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialLotDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.model.MaterialLot;

@Mapper(uses = { MaterialMapper.class, InvoiceItemMapper.class, StorageMapper.class, QuantityMapper.class })
public interface MaterialLotMapper extends BaseMapper<MaterialLot, MaterialLotDto, AddMaterialLotDto, UpdateMaterialLotDto> {
    MaterialLotMapper INSTANCE = Mappers.getMapper(MaterialLotMapper.class);

    @Mapping(target = MaterialLot.ATTR_ID)
    @Mapping(target = MaterialLot.ATTR_INDEX, ignore = true)
    @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialLot.ATTR_VERSION, ignore = true)
    @Mapping(target = MaterialLot.ATTR_QUANTITY, ignore = true)
    @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, ignore = true)
    @Mapping(target = MaterialLot.ATTR_STORAGE, ignore = true)
    @Mapping(target = MaterialLot.ATTR_LOT_NUMBER, ignore = true)
    MaterialLot fromDto(Long id);

    @Override
    MaterialLotDto toDto(MaterialLot lot);

    @Override
    @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INDEX, ignore = true)
    @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, source = "invoiceItemId")
    @Mapping(target = MaterialLot.ATTR_STORAGE, source = "storageId")
    MaterialLot fromUpdateDto(UpdateMaterialLotDto item);

    @Override
    @Mapping(target = MaterialLot.ATTR_ID, ignore = true)
    @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INDEX, ignore = true)
    @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, source = "invoiceItemId")
    @Mapping(target = MaterialLot.ATTR_STORAGE, source = "storageId")
    @Mapping(target = MaterialLot.ATTR_VERSION, ignore = true)
    MaterialLot fromAddDto(AddMaterialLotDto item);
}
