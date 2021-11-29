package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.BaseMapper;

@Mapper(uses = { ProcurementIdMapper.class, ProcurementItemMapper.class, ProcurementInvoiceMapper.class, ProcurementPurchaseOrderMapper.class, ProcurementShipmentMapper.class })
public interface ProcurementMapper extends BaseMapper<Procurement, ProcurementDto, AddProcurementDto, UpdateProcurementDto>{
    ProcurementMapper INSTANCE = Mappers.getMapper(ProcurementMapper.class);

    @Override
    ProcurementDto toDto(Procurement procurement);

    @Override
    Procurement fromAddDto(AddProcurementDto dto);

    @Override
    Procurement fromUpdateDto(UpdateProcurementDto dto);
}
