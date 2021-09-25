package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddSkuDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.UpdateSkuDto;
import io.company.brewcraft.model.Sku;

@Mapper(uses = { ProductMapper.class, QuantityMapper.class, QuantityUnitMapper.class, SkuMaterialMapper.class })
public interface SkuMapper {

    SkuMapper INSTANCE = Mappers.getMapper(SkuMapper.class);

    Sku fromDto(SkuDto dto);

    @Mapping(target = Sku.ATTR_ID, ignore = true)
    @Mapping(target = Sku.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Sku.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Sku.ATTR_VERSION, ignore = true)
    @Mapping(target = Sku.ATTR_PRODUCT, source = "productId")
    Sku fromDto(AddSkuDto dto);

    Sku fromDto(Long id);

    @Mapping(target = Sku.ATTR_ID, ignore = true)
    @Mapping(target = Sku.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Sku.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Sku.ATTR_PRODUCT, source = "productId")
    Sku fromDto(UpdateSkuDto dto);

    SkuDto toDto(Sku brew);

}
