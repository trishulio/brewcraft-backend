package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.UpdateMixtureDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Mixture;

@Mapper(uses = { EquipmentMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MaterialPortionMapper.class, MoneyMapper.class, BrewStageMapper.class, MaterialPortionMapper.class, MixtureRecordingMapper.class})
public interface MixtureMapper {

    MixtureMapper INSTANCE = Mappers.getMapper(MixtureMapper.class);

    Mixture fromDto(Long id);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Brew.ATTR_VERSION, ignore = true)
    @Mapping(target = "parentMixture", source = "parentMixtureId")
    @Mapping(target = "equipment", source = "equipmentId")
    @Mapping(target = "brewStage", source = "brewStageId")
    Mixture fromDto(AddMixtureDto dto);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "parentMixture", source = "parentMixtureId")
    @Mapping(target = "equipment", source = "equipmentId")
    @Mapping(target = "brewStage", source = "brewStageId")
    Mixture fromDto(UpdateMixtureDto dto);

    @Mapping(target = "parentMixtureId", source = "parentMixture.id")
    MixtureDto toDto(Mixture mixture);

}
