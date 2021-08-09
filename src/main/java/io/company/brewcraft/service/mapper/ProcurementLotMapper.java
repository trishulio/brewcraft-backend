package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ProcurementLotDto;
import io.company.brewcraft.model.ProcurementLot;


@Mapper(uses = { MaterialMapper.class, InvoiceItemMapper.class, StorageMapper.class, QuantityMapper.class })
public interface ProcurementLotMapper {
    ProcurementLotMapper INSTANCE = Mappers.getMapper(ProcurementLotMapper.class);

    ProcurementLotDto toDto(ProcurementLot lot);
}
