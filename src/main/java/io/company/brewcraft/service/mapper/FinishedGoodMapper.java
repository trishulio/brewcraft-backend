package io.company.brewcraft.service.mapper;

import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddFinishedGoodDto;
import io.company.brewcraft.dto.FinishedGoodDto;
import io.company.brewcraft.dto.UpdateFinishedGoodDto;
import io.company.brewcraft.model.FinishedGood;

@Mapper(uses = { SkuMapper.class, FinishedGoodMaterialPortionMapper.class, FinishedGoodMixturePortionMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class})
public interface FinishedGoodMapper {

    FinishedGoodMapper INSTANCE = Mappers.getMapper(FinishedGoodMapper.class);

    FinishedGood fromDto(Long id);

    @Mapping(target = "parentFinishedGoodId", source = "parentFinishedGood.id")
    FinishedGoodDto toDto(FinishedGood finishedGood);

    @Mapping(target = "parentFinishedGood", source = "parentFinishedGoodId")
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

    @AfterMapping
    default void afterFromDto(@MappingTarget FinishedGood finishedGood, AddFinishedGoodDto addFinishedGoodDto) {
        if (addFinishedGoodDto.getChildFinishedGoodIds() != null && !addFinishedGoodDto.getChildFinishedGoodIds().isEmpty()) {
            finishedGood.setChildFinishedGoods(addFinishedGoodDto.getChildFinishedGoodIds().stream().map(childId -> new FinishedGood(childId)).collect(Collectors.toList()));
        }
    }

    @AfterMapping
    default void afterFromDto(@MappingTarget FinishedGood finishedGood, UpdateFinishedGoodDto updateFinishedGoodDto) {
        if (updateFinishedGoodDto.getChildFinishedGoodIds() != null && !updateFinishedGoodDto.getChildFinishedGoodIds().isEmpty()) {
            finishedGood.setChildFinishedGoods(updateFinishedGoodDto.getChildFinishedGoodIds().stream().map(childId -> new FinishedGood(childId)).collect(Collectors.toList()));
        }
    }
}
