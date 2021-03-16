package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.pojo.Shipment;

@Mapper(uses = {TaxMapper.class, MaterialMapper.class, QuantityMapper.class, QuantityUnitMapper.class, CurrencyMapper.class, MoneyMapper.class})
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    Shipment fromDto(ShipmentDto dto);

    ShipmentDto toDto(Shipment shipment);
}
