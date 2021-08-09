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
import io.company.brewcraft.model.ProductCategory;

@Mapper()
public interface ProductCategoryMapper {

    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    CategoryDto toDto(ProductCategory productCategory);

    CategoryWithParentDto toCategoryWithParentDto(ProductCategory productCategory);

    ProductCategory fromDto(CategoryDto productCategoryDto);

    ProductCategory fromDto(AddCategoryDto productCategoryDto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    ProductCategory fromDto(UpdateCategoryDto productCategoryDto);

}
