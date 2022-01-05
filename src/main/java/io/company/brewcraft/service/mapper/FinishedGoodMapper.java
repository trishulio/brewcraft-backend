package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFinishedGoodDto;
import io.company.brewcraft.dto.FinishedGoodDto;
import io.company.brewcraft.dto.UpdateFinishedGoodDto;
import io.company.brewcraft.model.FinishedGood;

@Mapper(uses = { SkuMapper.class, FinishedGoodMaterialPortionMapper.class, FinishedGoodMixturePortionMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class})
public interface FinishedGoodMapper {

    FinishedGoodMapper INSTANCE = Mappers.getMapper(FinishedGoodMapper.class);

    FinishedGoodDto toDto(FinishedGood finishedGood);

    FinishedGood fromDto(FinishedGoodDto dto);

    @Mapping(target = FinishedGood.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = FinishedGood.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = FinishedGood.ATTR_SKU, source = "skuId")
    FinishedGood fromDto(UpdateFinishedGoodDto dto);

    @Mapping(target = FinishedGood.ATTR_ID, ignore = true)
    @Mapping(target = FinishedGood.ATTR_VERSION, ignore = true)
    @Mapping(target = FinishedGood.ATTR_SKU, source = "skuId")
    @Mapping(target = FinishedGood.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = FinishedGood.ATTR_CREATED_AT, ignore = true)
    FinishedGood fromDto(AddFinishedGoodDto dto);
}
