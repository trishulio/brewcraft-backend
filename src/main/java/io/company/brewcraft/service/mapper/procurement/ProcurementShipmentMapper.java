package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementShipmentDto;
import io.company.brewcraft.dto.procurement.ProcurementShipmentDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementShipmentDto;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.service.mapper.MaterialLotMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.service.mapper.ShipmentStatusMapper;

@Mapper(uses = {MaterialLotMapper.class, ShipmentStatusMapper.class, InvoiceMapper.class, ShipmentStatusMapper.class, QuantityMapper.class})
public interface ProcurementShipmentMapper   extends BaseMapper<Shipment, ProcurementShipmentDto, AddProcurementShipmentDto, UpdateProcurementShipmentDto>{
    ProcurementShipmentMapper  INSTANCE = Mappers.getMapper(ProcurementShipmentMapper .class);

    @Override
    ProcurementShipmentDto toDto(Shipment shipment);

    @Override
    @Mapping(target = Shipment.ATTR_ID, ignore = true)
    @Mapping(target = Shipment.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Shipment.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Shipment.ATTR_VERSION, ignore = true)
    @Mapping(target = Shipment.ATTR_SHIPMENT_STATUS, source = "shipmentStatusId")
    Shipment fromAddDto(AddProcurementShipmentDto dto);

    @Override
    @Mapping(target = Shipment.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Shipment.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Shipment.ATTR_SHIPMENT_STATUS, source = "shipmentStatusId")
    Shipment fromUpdateDto(UpdateProcurementShipmentDto dto);
}
