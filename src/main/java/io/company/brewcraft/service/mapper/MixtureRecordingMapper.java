package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddBrewStageDto;
import io.company.brewcraft.dto.AddMixtureRecordingDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.UpdateBrewStageDto;
import io.company.brewcraft.dto.UpdateMixtureRecordingDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.MixtureRecording;

@Mapper(uses = {  })
public interface MixtureRecordingMapper {

    MixtureRecordingMapper INSTANCE = Mappers.getMapper(MixtureRecordingMapper.class);

    @Mapping(target = "name", source = "productMeasure.name")
    MixtureRecordingDto toDto(MixtureRecording mixtureRecording); 
    
    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = Brew.ATTR_VERSION, ignore = true)
    @Mapping(target = "productMeasure.name", source = "name")
    MixtureRecording fromDto(AddMixtureRecordingDto dto);
    
    @Mapping(target = Brew.ATTR_ID, ignore = true)
    @Mapping(target = Brew.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Brew.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = "productMeasure.name", source = "name")
    MixtureRecording fromDto(UpdateMixtureRecordingDto dto);
}
