package io.company.brewcraft.service.mapper;

import javax.measure.Quantity;
import javax.measure.Unit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.QuantityDto;
import tec.units.ri.quantity.Quantities;

@Mapper
public interface QuantityMapper {
    QuantityMapper INSTANCE = Mappers.getMapper(QuantityMapper.class);

    @Mappings({ @Mapping(source = "unit.symbol", target = "symbol") })
    QuantityDto toDto(Quantity<?> quantity);

    default Quantity<?> fromDto(QuantityDto dto) {
        Unit<?> unit = UnitMapper.INSTANCE.getUnit(dto.getSymbol());
        return Quantities.getQuantity(dto.getValue(), unit);
    }
}
