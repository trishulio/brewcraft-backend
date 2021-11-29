package io.company.brewcraft.service.mapper.procurement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.service.mapper.BaseMapper;

@Mapper(uses = { ProcurementItemIdMapper.class, ProcurementInvoiceItemMapper.class, ProcurementMaterialLotMapper.class })
public interface ProcurementItemMapper extends BaseMapper<ProcurementItem, ProcurementItemDto, AddProcurementItemDto, UpdateProcurementItemDto> {
    ProcurementItemMapper INSTANCE = Mappers.getMapper(ProcurementItemMapper.class);

    @Override
    ProcurementItemDto toDto(ProcurementItem procurementItem);

    @Override
    ProcurementItem fromAddDto(AddProcurementItemDto dto);

    @Override
    ProcurementItem fromUpdateDto(UpdateProcurementItemDto dto);
}
