package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.model.MixtureRecording;

@Mapper(uses = {  })
public interface MixtureRecordingMapper {

    MixtureRecordingMapper INSTANCE = Mappers.getMapper(MixtureRecordingMapper.class);

    @Mapping(target = "productMeasure.name", source = "name")
    MixtureRecording fromDto(MixtureRecordingDto dto);

    @Mapping(target = "name", source = "productMeasure.name")
    MixtureRecordingDto toDto(MixtureRecording mixtureRecording); 
}
