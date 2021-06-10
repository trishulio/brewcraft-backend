package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddBrewStageDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.UpdateBrewStageDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;

@Mapper(uses = { })
public interface BrewStageMapper {

    BrewStageMapper INSTANCE = Mappers.getMapper(BrewStageMapper.class);

    @Mapping(target = "task.name", source = "task")
    @Mapping(target = "status.name", source = "status")
    BrewStage fromDto(BrewStageDto dto);

    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Brew.ATTR_VERSION, ignore = true)
    @Mapping(target = "task.id", source = "taskId")
    @Mapping(target = "status.id", source = "statusId")
    BrewStage fromDto(AddBrewStageDto dto);
    
    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "task.id", source = "taskId")
    @Mapping(target = "status.id", source = "statusId")
    BrewStage fromDto(UpdateBrewStageDto dto);

    @Mapping(target = "task", source = "task.name")
    @Mapping(target = "status", source = "status.name")
    @Mapping(target = "brewId", source = "brew.id")
    BrewStageDto toDto(BrewStage brew);

}
