package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.ProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementMaterialLotDto;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.service.mapper.InvoiceItemMapper;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.service.mapper.StorageMapper;

@Mapper(uses = { MaterialMapper.class, InvoiceItemMapper.class, StorageMapper.class, QuantityMapper.class })
public interface ProcurementMaterialLotMapper extends BaseMapper<MaterialLot, ProcurementMaterialLotDto, AddProcurementMaterialLotDto, UpdateProcurementMaterialLotDto> {
    ProcurementMaterialLotMapper INSTANCE = Mappers.getMapper(ProcurementMaterialLotMapper.class);

    @Override
    ProcurementMaterialLotDto toDto(MaterialLot lot);

    @Override
    @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, ignore = true)
    @Mapping(target = MaterialLot.ATTR_STORAGE, source = "storageId")
    MaterialLot fromUpdateDto(UpdateProcurementMaterialLotDto item);

    @Override
    @Mapping(target = MaterialLot.ATTR_ID, ignore = true)
    @Mapping(target = MaterialLot.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = MaterialLot.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_SHIPMENT, ignore = true)
    @Mapping(target = MaterialLot.ATTR_INVOICE_ITEM, ignore = true)
    @Mapping(target = MaterialLot.ATTR_STORAGE, source = "storageId")
    @Mapping(target = MaterialLot.ATTR_VERSION, ignore = true)
    MaterialLot fromAddDto(AddProcurementMaterialLotDto item);
}
