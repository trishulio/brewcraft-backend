package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.BrewStageStatusDto;
import io.company.brewcraft.model.BrewStageStatus;

@Mapper
public interface BrewStageStatusMapper {
	BrewStageStatusMapper INSTANCE = Mappers.getMapper(BrewStageStatusMapper.class);

    BrewStageStatus fromDto(Long id);

    BrewStageStatus fromDto(BrewStageStatusDto status);

    BrewStageStatusDto toDto(BrewStageStatus status);
}
