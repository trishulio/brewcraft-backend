package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ProductMeasureDto;
import io.company.brewcraft.model.ProductMeasureValue;

@Mapper(uses = {  })
public interface ProductMeasureValueMapper {

    ProductMeasureValueMapper INSTANCE = Mappers.getMapper(ProductMeasureValueMapper.class);

    @Mapping(target = "productMeasure.name", source = "name")
    ProductMeasureValue fromDto(ProductMeasureDto dto);

    @Mapping(target = "name", source = "productMeasure.name")
    ProductMeasureDto toDto(ProductMeasureValue product); 
}
