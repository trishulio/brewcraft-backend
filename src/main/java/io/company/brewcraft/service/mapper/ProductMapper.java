package io.company.brewcraft.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.Product;

@Mapper(uses = { ProductMeasuresMapper.class, ProductCategoryMapper.class })
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product fromDto(ProductDto dto);

    @Mapping(target = "category.id", source = "categoryId")
    Product fromDto(AddProductDto dto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "category.id", source = "categoryId")
    Product fromDto(UpdateProductDto dto);

    ProductDto toDto(Product product);
    
    @BeforeMapping
    default void beforetoDto(@MappingTarget ProductDto productDto, Product product) {
        ProductCategoryMapper productCategoryMapper = ProductCategoryMapper.INSTANCE;
        ProductCategory category = product.getCategory();
        
        if (category.getParentCategory() == null) {
            productDto.setProductClass(productCategoryMapper.toDto(category));
        } else if (category.getParentCategory().getParentCategory() == null) {
            productDto.setProductClass(productCategoryMapper.toDto(category.getParentCategory()));
            productDto.setType(productCategoryMapper.toDto(category));
        } else {
            productDto.setProductClass(productCategoryMapper.toDto(category.getParentCategory().getParentCategory()));
            productDto.setType(productCategoryMapper.toDto(category.getParentCategory()));
            productDto.setStyle(productCategoryMapper.toDto(category));
        }
    }

    //Mapstruct creates a new category entity even if the source categoryId is null, this is a workaround for the issue
    //See for more details: https://github.com/mapstruct/mapstruct/issues/879#issuecomment-346479822
    @AfterMapping
    default void afterFromDto(@MappingTarget Product target, UpdateProductDto productDto) {
        if (target.getCategory().getId() == null) {
            target.setCategory(null);
        }
    }
    
    @AfterMapping
    default void afterFromDto(@MappingTarget Product product, ProductDto productDto) {
        ProductCategoryMapper productCategoryMapper = ProductCategoryMapper.INSTANCE; 
        
        CategoryDto categoryDto = null;
        if (productDto.getStyle() != null) {
            categoryDto = productDto.getStyle();
        } else if (productDto.getType() != null) {
            categoryDto = productDto.getType();
        } else {
            categoryDto = productDto.getProductClass();
        }
        
        product.setCategory(productCategoryMapper.fromDto(categoryDto));
    }
}
