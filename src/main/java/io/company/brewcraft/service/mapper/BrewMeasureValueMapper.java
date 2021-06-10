package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ProductMeasureDto;
import io.company.brewcraft.model.BrewMeasureValue;

@Mapper(uses = {  })
public interface BrewMeasureValueMapper {

    BrewMeasureValueMapper INSTANCE = Mappers.getMapper(BrewMeasureValueMapper.class);

    @Mapping(target = "productMeasure.name", source = "name")
    BrewMeasureValue fromDto(ProductMeasureDto dto);

    @Mapping(target = "name", source = "productMeasure.name")
    ProductMeasureDto toDto(BrewMeasureValue brewMeasureValue); 
}
