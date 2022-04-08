package io.company.brewcraft.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.UpdateMixtureDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Mixture;

@Mapper(uses = { EquipmentMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MoneyMapper.class, BrewStageMapper.class})
public interface MixtureMapper {
    MixtureMapper INSTANCE = Mappers.getMapper(MixtureMapper.class);

    Mixture fromDto(Long id);

    List<Mixture> fromDto(Set<Long> parentMixtureIds);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Brew.ATTR_VERSION, ignore = true)
    @Mapping(target = "parentMixtures", source = "parentMixtureIds")
    @Mapping(target = "equipment", source = "equipmentId")
    @Mapping(target = "brewStage", source = "brewStageId")
    Mixture fromDto(AddMixtureDto dto);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "parentMixtures", source = "parentMixtureIds")
    @Mapping(target = "equipment", source = "equipmentId")
    @Mapping(target = "brewStage", source = "brewStageId")
    Mixture fromDto(UpdateMixtureDto dto);

    MixtureDto toDto(Mixture mixture);

    @AfterMapping
    default void afterToDto(@MappingTarget MixtureDto mixtureDto, Mixture mixture) {
        if (mixture.getParentMixtures() != null && !mixture.getParentMixtures().isEmpty()) {
            mixtureDto.setParentMixtureIds(mixture.getParentMixtures().stream().map(parentMixture -> parentMixture.getId()).collect(Collectors.toSet()));
        }
    }
}
