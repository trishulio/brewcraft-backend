package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ProductMeasureDto;
import io.company.brewcraft.model.ProductMeasure;

@Mapper(uses = {  })
public interface ProductMeasureMapper {

	ProductMeasureMapper INSTANCE = Mappers.getMapper(ProductMeasureMapper.class);

    ProductMeasure fromDto(ProductMeasureDto dto);

    ProductMeasureDto toDto(ProductMeasure productMeasureValue); 
}
