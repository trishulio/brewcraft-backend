package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.UpdateMixtureDto;
import io.company.brewcraft.model.Mixture;

@Mapper(uses = { EquipmentMapper.class, QuantityMapper.class, QuantityUnitMapper.class, MaterialPortionMapper.class, MoneyMapper.class, BrewStageMapper.class})
public interface MixtureMapper {

	MixtureMapper INSTANCE = Mappers.getMapper(MixtureMapper.class);

    Mixture fromDto(MixtureDto dto);

    Mixture fromDto(AddMixtureDto dto);

    Mixture fromDto(UpdateMixtureDto dto);

    @Mapping(target = "parentMixtureId", source = "parentMixture.id")
    MixtureDto toDto(Mixture mixture);

}
