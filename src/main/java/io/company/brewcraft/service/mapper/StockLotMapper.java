package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.StockLotDto;
import io.company.brewcraft.model.StockLot;


@Mapper(uses = { MaterialMapper.class, InvoiceItemMapper.class, StorageMapper.class, QuantityMapper.class })
public interface StockLotMapper {
    StockLotMapper INSTANCE = Mappers.getMapper(StockLotMapper.class);

    StockLotDto toDto(StockLot lot);
}
