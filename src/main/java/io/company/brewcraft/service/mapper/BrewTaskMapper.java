package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.BrewTaskDto;
import io.company.brewcraft.model.BrewTask;

@Mapper
public interface BrewTaskMapper {
	BrewTaskMapper INSTANCE = Mappers.getMapper(BrewTaskMapper.class);

	BrewTask fromDto(Long id);

	BrewTask fromDto(BrewTaskDto taskDto);

	BrewTaskDto toDto(BrewTask task);
}
