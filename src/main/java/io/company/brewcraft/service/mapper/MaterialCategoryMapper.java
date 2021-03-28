package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddCategoryDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.CategoryWithParentDto;
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.pojo.Category;

@Mapper()
public interface MaterialCategoryMapper {

    MaterialCategoryMapper INSTANCE = Mappers.getMapper(MaterialCategoryMapper.class);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    CategoryDto toDto(Category materialCategory);
    
    CategoryWithParentDto toCategoryWithParentDto(Category materialCategory);

    @InheritInverseConfiguration
    Category fromEntity(MaterialCategoryEntity materialCategoryEntity, @Context CycleAvoidingMappingContext context);

    Category fromDto(CategoryDto materialCategoryDto);

    Category fromDto(AddCategoryDto materialCategoryDto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Category fromDto(UpdateCategoryDto materialCategoryDto);

    MaterialCategoryEntity toEntity(Category materialCategory, @Context CycleAvoidingMappingContext context);

}
