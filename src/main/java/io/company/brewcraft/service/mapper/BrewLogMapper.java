package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddBrewLogDto;
import io.company.brewcraft.dto.BrewLogDto;
import io.company.brewcraft.model.BrewLog;

@Mapper(uses = { BrewStageMapper.class })
public interface BrewLogMapper {

	BrewLogMapper INSTANCE = Mappers.getMapper(BrewLogMapper.class);

    @Mapping(target = "type.name", source = "type")
    BrewLog fromDto(BrewLogDto dto);

    //@Mapping(target = "type.name", source = "type")
    BrewLog fromDto(AddBrewLogDto dto);

    @Mapping(target = "type", source = "type.name")
    //@Mapping(target = "userId", source = "user.id")
    BrewLogDto toDto(BrewLog brewLog);
    
}
