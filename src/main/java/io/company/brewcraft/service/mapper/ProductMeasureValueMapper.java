package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddProductMeasureValueDto;
import io.company.brewcraft.dto.ProductMeasureValueDto;
import io.company.brewcraft.model.ProductMeasureValue;

@Mapper(uses = { MeasureMapper.class })
public interface ProductMeasureValueMapper {

    ProductMeasureValueMapper INSTANCE = Mappers.getMapper(ProductMeasureValueMapper.class);

    ProductMeasureValue fromDto(ProductMeasureValueDto dto);

    ProductMeasureValueDto toDto(ProductMeasureValue productMeasureValue);

    @Mapping(target = "measure.id", source = "measureId")
    ProductMeasureValue fromDto(AddProductMeasureValueDto dto);
}
