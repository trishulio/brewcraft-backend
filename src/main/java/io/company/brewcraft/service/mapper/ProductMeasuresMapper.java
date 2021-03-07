package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.ProductMeasuresDto;
import io.company.brewcraft.model.ProductMeasuresEntity;
import io.company.brewcraft.pojo.ProductMeasures;

@Mapper()
public interface ProductMeasuresMapper {

    ProductMeasuresMapper INSTANCE = Mappers.getMapper(ProductMeasuresMapper.class);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    ProductMeasures fromDto(ProductMeasuresDto dto);

    ProductMeasuresDto toDto(ProductMeasures productMeasures);
    
    @InheritInverseConfiguration
    ProductMeasures fromEntity(ProductMeasuresEntity entity, @Context CycleAvoidingMappingContext context);

    ProductMeasuresEntity toEntity(ProductMeasures productMeasures , @Context CycleAvoidingMappingContext context);
    
}
