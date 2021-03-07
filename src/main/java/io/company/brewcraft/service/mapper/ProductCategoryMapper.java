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
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.model.ProductCategoryEntity;
import io.company.brewcraft.pojo.Category;

@Mapper()
public interface ProductCategoryMapper {

    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    CategoryDto toDto(Category productCategory);

    @InheritInverseConfiguration
    Category fromEntity(ProductCategoryEntity productCategoryEntity, @Context CycleAvoidingMappingContext context);

    Category fromDto(CategoryDto productCategoryDto);

    Category fromDto(AddCategoryDto productCategoryDto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Category fromDto(UpdateCategoryDto productCategoryDto);

    ProductCategoryEntity toEntity(Category productCategory, @Context CycleAvoidingMappingContext context);

}
