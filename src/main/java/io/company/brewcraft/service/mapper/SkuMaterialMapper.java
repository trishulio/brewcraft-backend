package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSkuMaterialDto;
import io.company.brewcraft.dto.SkuMaterialDto;
import io.company.brewcraft.dto.UpdateSkuMaterialDto;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuMaterial;

@Mapper(uses = { MaterialMapper.class, QuantityMapper.class })
public interface SkuMaterialMapper {
    SkuMaterialMapper INSTANCE = Mappers.getMapper(SkuMaterialMapper.class);

    @Mapping(target = SkuMaterial.ATTR_MATERIAL, source = "materialId")
    @Mapping(target = SkuMaterial.ATTR_ID, ignore = true)
    @Mapping(target = SkuMaterial.ATTR_SKU, ignore = true)
    @Mapping(target = SkuMaterial.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = SkuMaterial.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = SkuMaterial.ATTR_VERSION, ignore = true)
    SkuMaterial fromDto(AddSkuMaterialDto dto);

    @Mapping(target = SkuMaterial.ATTR_MATERIAL, source = "materialId")
    @Mapping(target = SkuMaterial.ATTR_SKU, ignore = true)
    @Mapping(target = SkuMaterial.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = SkuMaterial.ATTR_CREATED_AT, ignore = true)
    SkuMaterial fromDto(UpdateSkuMaterialDto dto);

    SkuMaterialDto toDto(SkuMaterial skuMaterial);
}
