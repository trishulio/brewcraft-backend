package io.company.brewcraft.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddCategoryDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.CategoryWithParentDto;
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.model.MaterialCategory;

@Mapper()
public interface MaterialCategoryMapper {

    MaterialCategoryMapper INSTANCE = Mappers.getMapper(MaterialCategoryMapper.class);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    CategoryDto toDto(MaterialCategory materialCategory);
    
    CategoryWithParentDto toCategoryWithParentDto(MaterialCategory materialCategory);

    MaterialCategory fromDto(CategoryDto materialCategoryDto);

    MaterialCategory fromDto(AddCategoryDto materialCategoryDto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    MaterialCategory fromDto(UpdateCategoryDto materialCategoryDto);

}
