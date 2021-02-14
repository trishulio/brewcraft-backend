package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialCategoryDto;
import io.company.brewcraft.dto.MaterialCategoryDto;
import io.company.brewcraft.dto.UpdateMaterialCategoryDto;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.pojo.MaterialCategory;

@Mapper()
public interface MaterialCategoryMapper {

    MaterialCategoryMapper INSTANCE = Mappers.getMapper(MaterialCategoryMapper.class);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    MaterialCategoryDto toDto(MaterialCategory materialCategory);

    @InheritInverseConfiguration
    MaterialCategory fromEntity(MaterialCategoryEntity materialCategoryEntity, @Context CycleAvoidingMappingContext context);

    MaterialCategory fromDto(MaterialCategoryDto materialCategoryDto);

    MaterialCategory fromDto(AddMaterialCategoryDto materialCategoryDto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    MaterialCategory fromDto(UpdateMaterialCategoryDto materialCategoryDto);

    MaterialCategoryEntity toEntity(MaterialCategory materialCategory, @Context CycleAvoidingMappingContext context);

}
