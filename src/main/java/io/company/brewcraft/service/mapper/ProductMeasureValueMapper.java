package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.AddProductMeasureValueDto;
import io.company.brewcraft.dto.ProductMeasureValueDto;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.ProductMeasureValue;

@Mapper(uses = { ProductMeasureMapper.class })
public interface ProductMeasureValueMapper {

    ProductMeasureValueMapper INSTANCE = Mappers.getMapper(ProductMeasureValueMapper.class);

    @Mapping(target = "productMeasure", source = "measure")
    ProductMeasureValue fromDto(ProductMeasureValueDto dto);

    @Mapping(target = "measure", source = "productMeasure")
    ProductMeasureValueDto toDto(ProductMeasureValue productMeasureValue); 
    
    @Mapping(target = "productMeasure.id", source = "measureId")
    ProductMeasureValue fromDto(AddProductMeasureValueDto dto);
}
