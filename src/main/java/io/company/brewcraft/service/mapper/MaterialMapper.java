package io.company.brewcraft.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMaterialDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.UpdateMaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialCategory;

@Mapper(uses = { QuantityUnitMapper.class, MaterialCategoryMapper.class })
public interface MaterialMapper {

    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    @Mapping(target = "baseQuantityUnit", source = "baseQuantityUnit")
    Material fromDto(MaterialDto dto);

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "baseQuantityUnit", source = "baseQuantityUnit")
    Material fromDto(AddMaterialDto dto);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "baseQuantityUnit", source = "baseQuantityUnit")
    Material fromDto(UpdateMaterialDto dto);

    @Mapping(target = "baseQuantityUnit", source = "baseQuantityUnit.symbol")
    @Mapping(target = "category", ignore = true)
    MaterialDto toDto(Material material);
    
    @Mappings({
        @Mapping(target = Material.ATTR_ID)
    })
    Material fromDto(Long id);

    @BeforeMapping
    default void beforetoDto(@MappingTarget MaterialDto materialDto, Material material) {
        MaterialCategoryMapper materialCategoryMapper = MaterialCategoryMapper.INSTANCE;
        MaterialCategory category = material.getCategory();
        
        if (category == null) {
            materialDto.setMaterialClass(null);
        } else if (category.getParentCategory() == null) {
            materialDto.setMaterialClass(materialCategoryMapper.toDto(category));
        } else if (category.getParentCategory().getParentCategory() == null) {
            materialDto.setMaterialClass(materialCategoryMapper.toDto(category.getParentCategory()));
            materialDto.setCategory(materialCategoryMapper.toDto(category));
        } else {
            materialDto.setMaterialClass(materialCategoryMapper.toDto(category.getParentCategory().getParentCategory()));
            materialDto.setCategory(materialCategoryMapper.toDto(category.getParentCategory()));
            materialDto.setSubcategory(materialCategoryMapper.toDto(category));
        }
    }

    //Mapstruct creates a new category entity even if the source categoryId is null, this is a workaround for the issue
    //See for more details: https://github.com/mapstruct/mapstruct/issues/879#issuecomment-346479822
    @AfterMapping
    default void afterFromDto(@MappingTarget Material target, UpdateMaterialDto materialDto) {
        if (target.getCategory().getId() == null) {
            target.setCategory(null);
        }
    }
    
    @AfterMapping
    default void afterFromDto(@MappingTarget Material material, MaterialDto materialDto) {
        MaterialCategoryMapper materialCategoryMapper = MaterialCategoryMapper.INSTANCE; 
        
        CategoryDto categoryDto = null;
        if (materialDto.getSubcategory() != null) {
            categoryDto = materialDto.getSubcategory();
        } else if (materialDto.getCategory() != null) {
            categoryDto = materialDto.getCategory();
        } else {
            categoryDto = materialDto.getMaterialClass();
        }
        
        material.setCategory(materialCategoryMapper.fromDto(categoryDto));
    }
}
